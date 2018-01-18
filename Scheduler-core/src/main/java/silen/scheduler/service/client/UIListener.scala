package silen.scheduler.service.client

import silen.scheduler.service.job.RootObserver
import silen.scheduler.job.data.NodeIdentity
import akka.actor.Actor
import silen.scheduler.service.job.MasterSystem




class UIListener  {
  
  val targetPath = "akka.tcp://UISystem@192.168.30.44:9527/user/UIManager"
  
   def update(ndi :NodeIdentity){
     
     
     
   }
   def handle(ndi :Any) {
     
     val target = MasterSystem.chooseActor(targetPath)
     
     target.!("task XXXX   prestart ")
     
   }
  
}