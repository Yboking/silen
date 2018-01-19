package silen.scheduler.service.client

import silen.scheduler.service.job.RootObserver
import silen.scheduler.job.data.NodeIdentity
import akka.actor.Actor
import silen.scheduler.service.job.MasterSystem
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem




class UIListener  {
  
  val targetPath = "akka.tcp://UISystem@192.168.30.44:9527/user/UIManager"
  
   def update(ndi :NodeIdentity){
     
     
     
   }
   def handle(ndi :Any) {
     
     val target = MasterSystem.chooseActor(targetPath)
     
     
     if(ndi.isInstanceOf[NodeIdentity]){
       
       val obj = ndi.asInstanceOf[NodeIdentity]
       
    	 target.!(s"task ${obj.id} started ")
       
     }
     
   }
  
}


object Main{
  
  def main(args: Array[String]): Unit = {
    
     val targetPath = "akka.tcp://UISystem@192.168.30.44:9527/user/UIManager"
  
      val pro = new java.util.Properties()
  pro.setProperty("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
  pro.setProperty("akka.remote.transport", "akka.remote.netty.NettyRemoteTransport")
  pro.setProperty("akka.remote.netty.tcp.port", "9528")

  val config = ConfigFactory.parseProperties(pro)

  val system = ActorSystem("MasterSystem", config)
     val target = system.actorSelection(targetPath)
     
     target.!("task XXXX   prestart ")
     
    
  }
}