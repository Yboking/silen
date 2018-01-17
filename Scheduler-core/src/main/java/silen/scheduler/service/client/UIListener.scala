package silen.scheduler.service.client

import silen.scheduler.service.job.RootObserver
import silen.scheduler.job.data.NodeIdentity
import akka.actor.Actor
import silen.scheduler.service.job.MasterSystem




class UIListener  {
  
  val targetPath = "akka.tcp://UISystem@127.0.0.1:9527/user/UIManager"
  
   def update(ndi :NodeIdentity){
     
     
     
   }
   def handle(ndi :Any) {
     
     val target = MasterSystem.chooseActor(targetPath)
     
     target.!("task XXXX   prestart ")
     
   }
  
}