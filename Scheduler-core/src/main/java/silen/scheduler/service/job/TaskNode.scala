package silen.scheduler.service.job

import akka.actor.Actor
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.SparkSession
import akka.actor.ActorRef
import silen.scheduler.data.job._

case class RootNode(actref: ActorRef, nid: NodeIdentity, preSender :ActorRef = null) {

  var taskType: String = null
  var command: Array[String] = null

  def this(actref: ActorRef) = {
    this(actref, null)
  }

  def getCommand() = command

  def setCommand(cmd: Array[String]) {
    this.command = cmd
  }

  def begin() {

    println("RootNode begin")
    actref.!(nid)(preSender)
  }

  def setTaskType(ct: String) = {
    taskType = ct
  }

}
//class TaskNode extends Actor {
//
//  val te = new TaskExecutor()
//  //TODO use queue instead
//  val linkedTask = ArrayBuffer[TaskDesc]()
//
//  def receive: Actor.Receive = {
//    case TaskType.RUN    => runTasks
//    case _ => {
//      //TODO unhandle exception   
//    }
//  }
//
//  def assignTask(t: TaskDesc) = {
//    linkedTask.append(t)
//  }
//
//  def runTasks() {
//    te.begin(getNextTask())
//  }
//
//  def getNextTask() = {
//    linkedTask(0)
//  }
//}

//import org.apache.spark.sql.DataFrame
//import org.apache.spark.sql.Row
//import akka.actor.ActorRef
//import akka.actor.ActorRef
//import org.apache.spark.sql.Dataset
//
//class TaskExecutor {
//
//  def parseAndExecute(keyValueArgs: Array[String]) = {}
//
//  import scala.collection.mutable._
//  import org.apache.spark.sql.Dataset
//
//  //    val functionName = args(0)
//
//  def begin(td: TaskDesc) {
//
//  }
//
//}

