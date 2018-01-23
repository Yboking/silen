package silen.scheduler.service.job

import silen.scheduler.event.TaskNodeCompleteEvent
import silen.scheduler.event.NodeEvent
import silen.scheduler.data.job.NodeIdentity


class NodeEventHandler {

  def handle(event: NodeEvent) {

    if (event.isInstanceOf[TaskNodeCompleteEvent]) {

      handleTaskComplete(event.asInstanceOf[TaskNodeCompleteEvent])

    }
    
    //TODO else other NodeEvent 
  }

  private def handleTaskComplete(tnEvent: TaskNodeCompleteEvent) {

    val did = tnEvent.getDataID
    
    JobContainer.setRunStatus(did)
    
    val user = JobContainer.getUser
    val job = JobContainer.getJob
    
    val node = JobContainer.findNode(NodeIdentity(user, job, did.consumerId))
    node.begin()

  }

}