//package silen.scheduler.service.job
//
//import scala.collection.mutable.ArrayBuffer
//
//import akka.actor.Actor
//import akka.actor.Props
//import silen.scheduler.service.client.UIManager
//import silen.scheduler.event.NodeEvent
//import silen.scheduler.event.NodePrestart
//import silen.scheduler.observer.NodePrestartObserver
//import silen.scheduler.data.job.TaskDesc
//import silen.scheduler.data.job.RenderData
//import silen.scheduler.data.job.NodeIdentity
//import silen.scheduler.data.job.TaskDesContainer
//import silen.scheduler.data.job.TaskType
//import silen.scheduler.data.job.MessageType
//import silen.scheduler.data.job.Message
//import silen.scheduler.event.JobCount
//import silen.scheduler.observer.JobCountObserver
//import scala.collection.mutable.HashMap
//import silen.scheduler.event.JobCount
//import silen.scheduler.utils.runtime.DateTimeUtil
//
//class EventPool2 {
//
//  val map = HashMap[Class[_], NodeEvent]()
//
//  def addEvent(e: NodeEvent) {
//
//    map.put(e.getClass, e)
//  }
//
//  def fireEvent(et: Class[_], data: Any) {
//
//    map.get(et) match {
//
//      case Some(e) => {
//        e.refresh(data)
//      }
//      case None => throw new Exception(s"no such event type $et")
//    }
//  }
//
//}
//
//class DataNodeManager2() extends Actor with NodeManager {
//
//  val UIM = new UIManager()
//
//  val eventPool = new EventPool()
//
//  val taskDesContainer = new TaskDesContainer()
//
//  override def preStart() {
//
//    eventPool.addEvent(new JobCount().registerObserver(new JobCountObserver()))
//
//  }
//
//  def handlePrestart(ndi: NodeIdentity) {
//
//    // NM update job status 
//    //    nodeEvent.refresh(ndi)
//
//    // render UI  
//    UIM.renderRequest(ndi)
//  }
//
//  def handleFirstNode(ndi: NodeIdentity) {
//    //TODO update job counter
//
//    val data = s"job${ndi.getJobId()} started at: ${DateTimeUtil.currentDateTime()}"
//    UIM.renderRequest(RenderData("jobStatus", data))
//
//  }
//
//  //TODO update the job counter
//  def handleOverNode(ndi: NodeIdentity) {
//    val data = s"job${ndi.getJobId()} end at: ${DateTimeUtil.currentDateTime()}"
//    UIM.renderRequest(RenderData("jobStatus", data))
//  }
//
//  override def handleFinish(ndi: NodeIdentity) {
//
//    eventPool.fireEvent(classOf[JobCount], ndi)
//    
//    
//  }
//
//  //TODO  handleFail
//  def handleFail(ndi: NodeIdentity) {
//
//  }
//
//  def receive: Actor.Receive = {
//
//    case td: TaskDesc => {
//      println(td)
//      if (td.ttype.equals(TaskType.ASSIGN)) {
//        addTask(td)
//
//      }
//      if (td.ttype.equals(TaskType.RUN)) {
//        run(td.getUserId(), td.getJobId())
//      }
//    }
//
//    case Message(mt, content) => {
//
//      mt match {
//        case MessageType.NODE_PRE_START => {
//          handlePrestart(content.asInstanceOf[NodeIdentity])
//          println(">>>>>>>>>>>>>>>>>>>>>>> NODE_PRE_START " + NodeIdentity)
//        }
//
//        case MessageType.NODE_FINISH => {
//
//          handleFinish(content.asInstanceOf[NodeIdentity])
//        }
//
//        case MessageType.NODE_FIRST => {
//
//          handleFirstNode(content.asInstanceOf[NodeIdentity])
//        }
//
//        case MessageType.NODE_OVER => {
//          handleOverNode(content.asInstanceOf[NodeIdentity])
//        }
//      }
//
//    }
//
//    case _ => println("unexpected happen..")
//  }
//
//  var tg: TaskGraph = null
//  def checkNodes(userId:String, jobId: String) = {
//
//    //init all nodes
//
//    val jobIdentity = userId + "_" + jobId
//    tg = TaskGraph(taskDesContainer.getTasksByJob(jobIdentity))
//    for (i <- 1 to tg.getNodeNum) {
//      val tmpnode = createNode(userId, jobId, i)
//      val node = new RootNode(JobContainer.createActor(Props[DataHandler]), tmpnode, this.self)
//      JobContainer.addNode(node)
//
//    }
//    tg.getNodeNum
//  }
//
//  def createNode(userId:String, jobId: String, id: Int): NodeIdentity = {
//
//    val jobIdentity = userId + "_" + jobId
//    val name = createOrGetNodeName(jobIdentity, id.toString())
//    val node = createNode(userId, jobId, id, false)
//    node.setName(name)
//    node
//  }
//
//  def createOrGetNodeName(jobIdentity: String, nodeId: String) = {
//
//    var nodeName = taskDesContainer.getNodeNameById(jobIdentity, nodeId)
//    nodeName
//
//  }
//
//  /**
//   * create single node wtih node id
//   */
//  private[scheduler] def createNode(userId:String, jobId:String, id: Int, emptyTask: Boolean = false): NodeIdentity = {
//
//    if (emptyTask) {
//     NodeIdentity(userId, jobId, id)
//    } else {
//      val nodeinfo = tg.getSingleNodeInfo(id)
//      val tmp = NodeIdentity(userId, jobId, id = id, cmd = nodeinfo)
//      val prenodes = for (i <- tg.getPrenodes(id)) yield createNode(userId, jobId,i, true)
//      val succnodes = for (i <- tg.getSuccnodes(id)) yield createNode(userId, jobId,i, true)
//      tmp.preNodes = prenodes
//      tmp.succNodes = succnodes
//      tmp
//    }
//  }
//
//  def run(userId:String, jobId: String) {
//
//    val numOfNodes = checkNodes(userId, jobId)
//
//    val jobIdentity = userId + "_" + jobId
//    eventPool.fireEvent(classOf[JobCount], (jobIdentity, numOfNodes))
//
//    for (node <- getParallelTasks(userId, jobId)) {
//      node.begin()
//    }
//
//    // TODO remove nodes already done
//
//    /**
//     * remove tasks from same job
//     */
//
//    taskDesContainer.removeTaskByJob(jobIdentity)
//  }
//
//  def addTask(td: TaskDesc) {
//
//    taskDesContainer.addTask(td.getJobFullId, td)
//  }
//
//  
//  //TODO fix the findnode bug  
//  def getParallelTasks(userId:String, jobId:String) = {
//
//    val nodes = tg.findStartNodes()
//    for (id <- nodes) yield {
//      val nid = createNode(userId, jobId, id)
//
//      println("getParallelTasks " + nid)
//      JobContainer.findNode(nid)
//
//    }
//    //    Array(TaskDesc(0, null, Array(), 1, 5))
//  }
//
//}
//
//
//    