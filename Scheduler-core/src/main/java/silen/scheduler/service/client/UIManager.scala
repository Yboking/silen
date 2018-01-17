package silen.scheduler.service.client

import silen.scheduler.service.job.RootObserver
import silen.scheduler.job.data.NodeIdentity

class UIManager {

  def renderRequest(obj: Any) {

    
   
    new UIListener().handle(obj)
  }
}

