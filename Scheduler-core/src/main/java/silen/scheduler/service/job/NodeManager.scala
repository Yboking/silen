package silen.scheduler.service.job

import silen.scheduler.job.data.NodeIdentity
import java.util.concurrent.ConcurrentHashMap

trait NodeManager extends RuntimeCounter {

  def handlePrestart(ndi: NodeIdentity)

  def handleRunning(ndi: NodeIdentity) {}

  def handleFinish(ndi: NodeIdentity) {}

  def handleFail(ndi: NodeIdentity)

}

trait RuntimeCounter {

  val counter = new ConcurrentHashMap[String, Long]()

  def getCounter() = {

    counter
  }

  def increase(counterName: String) {
    val cur = counter.getOrDefault(counterName, 0)
    counter.put(counterName, cur + 1)
  }

  def increase(counterKeys: Array[Any]) {
    increase(counterKeys.mkString("."))
  }

  def getCounterValue(counterName: String) = counter.get(counterName)

  def toJson() = {

  }

}