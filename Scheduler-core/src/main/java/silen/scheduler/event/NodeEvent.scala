package silen.scheduler.event

import scala.collection.mutable.ArrayBuffer
import silen.scheduler.service.job.RootObserver
import silen.scheduler.data.job.NodeIdentity

abstract class NodeEvent {

  private val observers = ArrayBuffer[RootObserver]()

  def registerObserver(ob: RootObserver) = {
    observers.+=(ob)
    this
  }

  def removeObserver(ob: RootObserver) {

    val index = observers.lastIndexOf(ob)
    if (index != -1) {

      observers.remove(index)
    }

  }

  def refresh(data: Any) {
    observers.foreach(_.update(data))
  }

  def refresh(ndi: NodeIdentity) {
    observers.foreach(_.update(ndi))
  }

}


