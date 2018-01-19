package silen.scheduler.service.job

import akka.actor.Actor
import scala.collection.mutable.HashMap
import akka.actor.ActorSystem
import akka.actor.Props
import scala.collection.mutable.ListBuffer
import JobContainer._
import silen.scheduler.job.data.TaskDesc
import silen.scheduler.job.data.NodeIdentity
import silen.scheduler.job.data.NodeIdentity
import silen.scheduler.job.data.TaskType
import silen.scheduler.job.data.Message
import silen.scheduler.job.data.MessageType
import silen.sheduler.event.NodePrestartObserver
import silen.scheduler.service.client.UIManager
import silen.scheduler.job.data.TaskDesContainer

class DataNodeManager() extends Actor with NodeManager {

  val nodeEvent = new NodePrestart()
  val dataHandlers = HashMap[Int, RootNode]()
  val taskDesContainer = new TaskDesContainer()    

  val UIM= new UIManager()
  
  
  
  
  def handlePrestart(ndi: NodeIdentity) {

    // NM update job status 
    nodeEvent.refresh(ndi)
    
    // render UI  
    UIM.renderRequest(ndi)
  }

  def handleFail(ndi: NodeIdentity) {

  }

  def receive: Actor.Receive = {

    case td: TaskDesc => {

      println(td)
      if (td.ttype.equals(TaskType.ASSIGN)) {
        addTask(td)

      }
      if (td.ttype.equals(TaskType.RUN)) {
        run(td.getJobId)
      }
    }

    case Message(mt, content) => {

      if (mt == MessageType.NODE_PRE_START) {
        handlePrestart(content.asInstanceOf[NodeIdentity])
        println(">>>>>>>>>>>>>>>>>>>>>>> NODE_PRE_START "  + NodeIdentity)
      }
    }

    case _ => println("unexpected happen..")
  }

  var tg: TaskGraph = null
  def checkNodes(jobIdentity:String ) {

    //init all nodes

    tg = TaskGraph(taskDesContainer.getTasksByJob(jobIdentity))
    for (i <- 1 to tg.getNodeNum) {
      val tmpnode = createNode(i)
      val node = new RootNode(JobContainer.createActor(Props[DataHandler]), tmpnode, this.self)
      JobContainer.addNode(node)

    }
  }

  def createNode(id: Int, emptyTask: Boolean = false): NodeIdentity = {

    if (emptyTask) {
      NodeIdentity(JobContainer.getUser, getJob, id)
    } else {
      val nodeinfo = tg.getSingleNodeInfo(id)
      val tmp = NodeIdentity(getUser, getJob, id, cmd = nodeinfo)
      val prenodes = for (i <- tg.getPrenodes(id)) yield createNode(i, true)
      val succnodes = for (i <- tg.getSuccnodes(id)) yield createNode(i, true)
      tmp.preNodes = prenodes
      tmp.succNodes = succnodes
      tmp
    }
  }

  def run(jobIdentity :String ) {

    checkNodes(jobIdentity)

    for (node <- getParallelTasks()) {
      node.begin()
    }
  }


  def addTask(td: TaskDesc) {

    taskDesContainer.addTask(td.getJobId, td) 
  }

  def getParallelTasks() = {

    val nodes = tg.findStartNodes()
    for (id <- nodes) yield {
      val nid = createNode(id, true)

      println("getParallelTasks " + nid)
      JobContainer.findNode(nid)

    }
    //    Array(TaskDesc(0, null, Array(), 1, 5))
  }

  override def preStart() {

    nodeEvent.registerObserver(new NodePrestartObserver())

  }
}


    