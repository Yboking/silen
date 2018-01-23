package silen.scheduler.observer

import silen.scheduler.service.job.RootObserver
import silen.scheduler.service.job.RuntimeCounter
import silen.scheduler.data.job.NodeIdentity

class NodePrestartObserver() extends RootObserver with RuntimeCounter {

  def update(ndi: NodeIdentity) {
    increase(Array(ndi.getUserId(), ndi.getJobId(), ndi.id))
  }
  
  def update(data:Any){
    
    
  }

}