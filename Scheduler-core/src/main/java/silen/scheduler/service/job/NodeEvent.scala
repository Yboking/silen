package silen.scheduler.service.job

import silen.scheduler.job.data.DataIdentity
import scala.collection.mutable.ArrayBuffer
import silen.scheduler.job.data.NodeIdentity

abstract class NodeEvent {

  private val observers = ArrayBuffer[RootObserver]()

  def registerObserver(ob: RootObserver) {
    observers.+=(ob)
  }

  def removeObserver(ob: RootObserver) {

    val index = observers.lastIndexOf(ob)
    if (index != -1) {

      observers.remove(index)
    }

  }

  def refresh(ndi: NodeIdentity) {
    observers.foreach(_.update(ndi))
  }

}

class NodePrestart extends NodeEvent {

   
  
}

case class TaskNodeCompleteEvent(id: DataIdentity) extends NodeEvent {
  def getDataID = id

  def registerObserver() {

  }

  def removeObserver() {

  }

  def refresh() {

  }
}