
package silen.scheduler.ui.server


import akka.actor.Actor
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props

object UIConnector extends App {

  val pro = new java.util.Properties()
  pro.setProperty("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
  pro.setProperty("akka.remote.transport", "akka.remote.netty.NettyRemoteTransport")
  pro.setProperty("akka.remote.netty.tcp.port", "9527")

  val config = ConfigFactory.parseProperties(pro)

  val system = ActorSystem("UISystem", config)
  val remoteActor = system.actorOf(Props[LocalRender], name = "UIManager")

  remoteActor !  "Test UIManager is alive ?"


}


class LocalRender extends Actor{
  
   def receive: Actor.Receive = {
     case  v :String => 
     println(v)
     
   }
  
     
     
     
}

