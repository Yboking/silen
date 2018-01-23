package silen.scheduler.observer

import silen.scheduler.service.job.RuntimeCounter
import silen.scheduler.data.job.NodeIdentity
import silen.scheduler.service.job.RootObserver
import scala.collection.mutable.HashMap
import silen.scheduler.service.client.UIManager
import silen.scheduler.data.job.RenderData

class JobCountObserver() extends RootObserver with RuntimeCounter {

  private val taskCountMap = HashMap[String, Int]()

  val UIM = new UIManager()
  def update(ndi: NodeIdentity) {
    //    increase(Array(ndi.userId, ndi.jobId, ndi.id))

    val curFinish = taskCountMap.getOrElse(finishKey(ndi.getJobFullId()), 0)
    taskCountMap.put(finishKey(ndi.getJobFullId()), curFinish + 1)
    
    
    
    val value = getJobProgress(ndi.getJobFullId())
    
    UIM.renderRequest(RenderData("jobProgressValue", value.toString()))
    
    
    
  }

  /**
   * return job progress percent
   */
  def getJobProgress(key: String) = {

    val curFinish = taskCountMap.getOrElse(finishKey(key), 0)

    val value = taskCountMap.get(key) match {

      case Some(c) => {

        if(c == 0) 0 else curFinish.toDouble / c
      }
      case None => {

        throw new Exception(s"no job progress found $key")
      }
    }
    (value * 100).toInt 

  }

  def update(data: Any) {

    if (data.isInstanceOf[NodeIdentity]) {

      update(data.asInstanceOf[NodeIdentity])

    } else {

      val tmpdata = data.asInstanceOf[(String, Int)]
      println(s"${this.getClass}  ${tmpdata._1} : ${tmpdata._2}")
      taskCountMap.put(tmpdata._1, tmpdata._2)
    }

  }

  def finishKey(s: String) = s + "_success"

}