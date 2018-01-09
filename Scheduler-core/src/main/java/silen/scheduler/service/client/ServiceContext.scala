package silen.scheduler.service.client
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.ActorPath
import java.io.InputStreamReader



object ServiceContext {

//	val config = ConfigFactory.parseReader(new InputStreamReader(this.getClass.getClassLoader.getResourceAsStream("application.conf")))
  val config = ConfigFactory.defaultApplication()
  
  
  implicit val system = ActorSystem("LocalSystem", config, this.getClass.getClassLoader)
  implicit val remoteActor = system.actorSelection(ActorPath.fromString(config.getString("akka.service.path")))
  
}



  