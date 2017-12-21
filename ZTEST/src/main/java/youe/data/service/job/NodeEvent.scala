package youe.data.service.job

import youe.data.service.job.data.DataIdentity



  trait NodeEvent

  
  
  
  
  case class TaskNodeCompleteEvent(id: DataIdentity) extends NodeEvent {
    def getDataID = id
  }