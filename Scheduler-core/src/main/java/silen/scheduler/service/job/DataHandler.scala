package silen.scheduler.service.job

import akka.actor.Actor
import scala.collection.mutable.HashMap
import akka.actor.ActorSystem
import akka.actor.Props
import scala.collection.mutable.ListBuffer
import JobContainer._
import org.apache.log4j.Logger
import silen.scheduler.data.job.MessageType._
import silen.scheduler.event.TaskNodeCompleteEvent
import silen.scheduler.utils.runtime.ServiceLogger
import silen.scheduler.data.job.TaskDesc
import silen.scheduler.data.job.NodeIdentity
import silen.scheduler.data.job.DataIdentity
import silen.scheduler.data.job.Message

case class DataHandler() extends Actor with ServiceLogger {

  val loger = Logger.getLogger(this.getClass)

  def isFirstNode(ndi: NodeIdentity) = {

    //TODO  if the wf is single mode 
    if (ndi.preNodes == null || ndi.preNodes.length == 0) {
      true
    } else
      false
    // TODO  if the wf is multiple mode    
  }

  def isLastNode(ndi: NodeIdentity): Boolean = {

    //TODO  if the wf is single mode 

    if (ndi.succNodes == null) {

      return true
    } else if (ndi.succNodes.length == 1) {
      val succ = ndi.succNodes(0)

      if (succ.id == ndi.id) {

        return true
      } else
        return false

    }

    if (ndi.succNodes == null || ndi.succNodes.length == 0) {
      true
    } else
      false
    // TODO  if the wf is multiple mode    
  }

  def firePrepareNode(ndi: NodeIdentity) {

    sendMessage(Message(NODE_PRE_START, ndi))
    //    sender.!(Message(NODE_PRE_START, ndi))

  }

  def fireFinishNode(ndi: NodeIdentity) {

    sendMessage(Message(NODE_FINISH, ndi))
  }
  /*
   * fisrt node in current job
   */
  def fireFirstNode(ndi: NodeIdentity) {

    sendMessage(Message(NODE_FIRST, ndi))
  }

  /*
   * last node in current job
   */

  def fireOverNode(ndi: NodeIdentity) {
    sendMessage(Message(NODE_OVER, ndi))
  }

  def receive: Actor.Receive = {

    case td: TaskDesc => {

      //      val res = TaskUtil.handleTask(td.command)
      //      val args = KeyValuePairArgsUtil.extractValueArgs(td.command)
      //      //       userId: String, jobId: String, producerId: Int, consumerId: Int, pathTag: String = "") 
      //      val data = DataRes(DataIdentity("user1", "job1", 1, 5, args(args.length - 1)), res)
      //      JobContainer.addDataRes(data)
      //      JobContainer.addEvent(TaskNodeCompleteEvent(data.id))
    }

    case ndi: NodeIdentity => {

      println("task : " + ndi)
      /**
       * not ready to run
       */
      if (ndi.preNodes != null && !checkRunnable(ndi.preNodes)) {

        println(s"task ${ndi} is not ready to run ")
        //TODO    do something
      } else {

        if (isFirstNode(ndi)) {
          fireFirstNode(ndi)
        }

        firePrepareNode(ndi)

        try {
          val nodedata = JobContainer.findNodeData(ndi.preNodes.map { x => DataIdentity(x.getUserId(), x.getJobId(), x.id, ndi.id) })
//          val args = KeyValuePairArgsUtil.extractValueArgs(ndi.cmd)
          val res = TaskUtil.handleTask(ndi, nodedata)
          for (node <- ndi.succNodes) {

            //TODO    make copy of res ?
            res.id = DataIdentity(ndi.getUserId(), ndi.getJobId(), ndi.id, node.id, ndi.actionParser.getOutputPaths().toString())
            //          val dr = DataRes(DataIdentity(JobContainer.getUser, JobContainer.getJob, ndi.id, node.id, args(args.length - 1)), res)
            JobContainer.addDataRes(res)
            if (ndi.id != node.id) {
              JobContainer.addEvent(TaskNodeCompleteEvent(res.id))
            }
          }

          fireFinishNode(ndi)

          if (isLastNode(ndi)) {
            fireOverNode(ndi)
            println("Node Over event " + ndi.getName() + "  " + ndi.id)

          }

        } catch {

          case e: Exception => {

            e.printStackTrace()
            loger.error(ndi.toString())
            loger.error(makeLoginfo(e))

          }
        }

      }
    }

    case _ => {

      println("worker start work..")
    }
  }

  def checkRunnable(nids: Array[NodeIdentity]): Boolean = {

    JobContainer.checkRunStatus(nids)

  }

  def sendMessage(m: Any) {
    sender.!(m)
  }

}