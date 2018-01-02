package youe.data.service.job

import akka.actor.Actor
import youe.data.service.job.data.TaskMessage
import youe.data.service.job.data.TaskType
import youe.data.service.job.data.TaskDesc
import scala.collection.mutable.HashMap
import akka.actor.ActorSystem
import akka.actor.Props
import youe.data.service.job.data.DataIdentity
import youe.data.service.job.data.DataRes
import scala.collection.mutable.ListBuffer
import youe.data.service.job.data.NodeIdentity
import JobContainer._
import com.param.utils.KeyValuePairArgsUtil
import org.apache.log4j.Logger
import com.runtime.utils.ServiceLogger

case class DataHandler() extends Actor with ServiceLogger {

  val loger = Logger.getLogger(this.getClass)
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
      //not ready to run
      if (ndi.preNodes != null && !checkRunnable(ndi.preNodes)) {

        println("not ready to run ")
        //do thing
      } else {

        try {
          val nodedata = JobContainer.findNodeData(ndi.preNodes.map { x => DataIdentity(x.userId, x.jobId, x.id, ndi.id) })
          val args = KeyValuePairArgsUtil.extractValueArgs(ndi.cmd)
          val res = TaskUtil.handleTask(ndi.cmd, nodedata)
          for (node <- ndi.succNodes) {

            //TODO    make copy of res ?
            res.id = DataIdentity(JobContainer.getUser, JobContainer.getJob, ndi.id, node.id, args(args.length - 1))
            //          val dr = DataRes(DataIdentity(JobContainer.getUser, JobContainer.getJob, ndi.id, node.id, args(args.length - 1)), res)
            JobContainer.addDataRes(res)
            if (ndi.id != node.id) {
              JobContainer.addEvent(TaskNodeCompleteEvent(res.id))
            }
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

}