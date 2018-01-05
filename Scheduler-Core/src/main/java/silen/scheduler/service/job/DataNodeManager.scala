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


class DataNodeManager() extends Actor {

  val dataHandlers = HashMap[Int, RootNode]()

  //  val node = JobContainer.createRootNode(Props(DataHandler()))
  //  dataHandlers.put(1, node)

  val taskList = ListBuffer[TaskDesc]()

  //  val taskgraph = new TaskGraph()

  def receive: Actor.Receive = {

    case td: TaskDesc => {
      
      println(td)
      if (td.ttype.equals(TaskType.ASSIGN)) {
        addTask(td)
      }
      if (td.ttype.equals(TaskType.RUN)) {
        run()
      }
    }


    case _               => println("unexpected happen..")
  }


  var tg: TaskGraph = null
  def checkNodes() {

    //init all nodes

    tg = TaskGraph(taskList.toArray)
    for (i <- 1 to tg.getNodeNum) {
      val tmpnode = createNode(i)
      val node = new RootNode(JobContainer.createActor(Props[DataHandler]), tmpnode)
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

  def run() {

    checkNodes()

    for (node <- getParallelTasks()) {
      node.begin()
    }
  }

  def getDataHandler(td: TaskDesc) {

  }

  def addTask(td: TaskDesc) {

    //    node.setTaskType(td.ttype)

    //    node.setCommand(td.command)

    //    dataHandlers.+=((td.source, node))

    taskList.+=(td)
  }

  // TaskDesc(id: Long, ttype: String, command: Array[String], source: Int, target: Int)

  def getParallelTasks() = {

    val nodes = tg.findStartNodes()
    for (id <- nodes) yield {
      val nid = createNode(id, true)
      
      println("getParallelTasks " + nid)
      JobContainer.findNode(nid)

    }
    //    Array(TaskDesc(0, null, Array(), 1, 5))
  }

}


    