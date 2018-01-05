package silen.scheduler.service.job

import silen.scheduler.job.data.NodeIdentity


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

  def getNextNode(taskType: Int) = {

    classOf[KD]

  }
}