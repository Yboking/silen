package silen.scheduler.service.job

import silen.scheduler.job.data.DataIdentity



  trait NodeEvent

  
  
  
  
  case class TaskNodeCompleteEvent(id: DataIdentity) extends NodeEvent {
    def getDataID = id
  }