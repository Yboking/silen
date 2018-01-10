package silen.scheduler.service.job

import silen.scheduler.job.data.NodeIdentity

abstract class RootObserver {
  
  
  def update(ndi :NodeIdentity)
  
}