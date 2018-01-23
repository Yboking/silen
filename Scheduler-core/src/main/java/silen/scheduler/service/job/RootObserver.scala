package silen.scheduler.service.job

import silen.scheduler.data.job.NodeIdentity


abstract class RootObserver {
  
  
  def update(ndi :NodeIdentity)
  
  
  def update(data:Any)
  
}