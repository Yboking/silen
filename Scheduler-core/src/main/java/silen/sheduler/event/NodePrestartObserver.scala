package silen.sheduler.event

import silen.scheduler.service.job.RootObserver
import silen.scheduler.job.data.NodeIdentity
import silen.scheduler.service.job.RuntimeCounter

class NodePrestartObserver() extends RootObserver with RuntimeCounter {

  def update(ndi: NodeIdentity) {
    increase(Array(ndi.userId, ndi.jobId, ndi.id))
  }
  
  

}