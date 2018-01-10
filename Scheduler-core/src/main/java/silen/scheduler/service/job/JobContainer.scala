package silen.scheduler.service.job

import scala.collection.mutable._
import scala.reflect.ClassTag

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import silen.scheduler.job.data.NodeIdentity
import silen.scheduler.job.data.DataRes
import silen.scheduler.job.data.DataIdentity

object JobContainer {

  val system = ActorSystem("RootSystem")

  val dataRef = HashMap[DataIdentity, DataRes[Any]]()
  val nodeRef = HashMap[NodeIdentity, RootNode]()
  val nodeStatus = HashMap[NodeIdentity, Byte]()
  val cmTable = HashMap[Int, String]()

  val handler = new NodeEventHandler

  def findNodeData(dataIDS: Array[DataIdentity]) = {

    if (dataIDS == null || dataIDS.length == 0)
      null
    else {

      val res = new Array[DataRes[Any]](dataIDS.length)

      for (i <- 0 until res.length) {

        res(i) = dataRef(dataIDS(i))
      }

      res

    }

  }

  def checkRunStatus(nids: Array[NodeIdentity]): Boolean = {

    for (nid <- nids) {
      if (getNodeStatus(nid) == 0) {
        return false
      }
    }
    true
  }

  def setRunStatus(did: DataIdentity) {

    val nid = NodeIdentity(did.userId, did.jobId, did.producerId)
    nodeStatus.+=((nid, 1))

  }

  def getNodeStatus(nid: NodeIdentity) = {

    
	  println(nodeStatus)
    nodeStatus.get(nid) match {
      case Some(s) => s

      case None    => 0 //   0 --> init status 
    }
  }

  def addDataRes(dataRes: DataRes[Any]*) {

    for (data <- dataRes) {

      dataRef.put(data.id, data)

    }
  }

  def addEvent(event: NodeEvent) {

    //TODO add to queue

    handler.handle(event)
  }

  def createActor(props: Props) = {
    //    system.actorOf(props)
    system.actorOf(props)
  }

  def createActor(actor: Actor) = {
    system.actorOf(Props(actor))
  }

  implicit def toActorProps = (actor: Actor) => Props(actor)

  //  def createRootNode(props: Props) = {
  //
  //    new RootNode(system.actorOf(props))
  //  }

  def addNode(nid: NodeIdentity, node: RootNode) {

    nodeRef.+=((nid, node))

  }

  def addNode(node: RootNode) {

    nodeRef.+=((node.nid, node))

  }

  def findNode(id: NodeIdentity) = {

    println(nodeRef)
    (nodeRef.get(id) match {
      case Some(node) => node
      case None => {
        // 
        println(">>>>>>>>>>>>> Not Found " + id)
      }
    }).asInstanceOf[RootNode]
  }

  def getUser = {

    "user"
  }

  def getJob = {

    "job"
  }

  def getCommandType(key: Int) = cmTable(key)

  def getNodeClass(commandType: String) = {

//    commandType match {
//      case "train" => {
//        classOf[KD]
//      }
//
//      //        case "etl" => {
//
//      //        }
//    }

  }

}