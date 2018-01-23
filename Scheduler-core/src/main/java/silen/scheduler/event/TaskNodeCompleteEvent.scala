package silen.scheduler.event

import silen.scheduler.data.job.DataIdentity


case class TaskNodeCompleteEvent(id: DataIdentity) extends NodeEvent {
  def getDataID = id

  def registerObserver() {

  }

  def removeObserver() {

  }

  def refresh() {

  }
}