
package silen.scheduler.service.job

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.OneForOneStrategy
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.routing.RoundRobinPool
import com.typesafe.config.ConfigFactory
import silen.scheduler.data.job.TaskDesc
import silen.scheduler.data.job.Message

object MasterSystem extends App {

  def setContextConfig(kvs: (String, String)*) {

    for (kv <- kvs) {
      System.setProperty(kv._1, kv._2)
    }

  }

  if (args != null) {
    val kvs = for (param <- args) yield {
      val kv = param.split("=")
      (kv(0), kv(1))
    }
    setContextConfig(kvs: _*)

  }
  
  println(" user lib : " + System.getProperty(ConfigKeys.USER_EXT_LIBS))

  val pro = new java.util.Properties()
  pro.setProperty("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
  pro.setProperty("akka.remote.transport", "akka.remote.netty.NettyRemoteTransport")
  pro.setProperty("akka.remote.netty.tcp.port", "9528")

  val config = ConfigFactory.parseProperties(pro)

  val system = ActorSystem("MasterSystem", config)
  val remoteActor = system.actorOf(Props(new JobScheduleListener(1, null)), name = "DataNodeManager")

  // Test Alive
  remoteActor ! Message(content = "The RemoteActor is alive")

  
  
  def getSystem() = this.system
  
  
  def chooseActor(path :String) = {
    
    system.actorSelection(path)
  }
}

class JobScheduleListener(nodeManagerNumber: Int, listener: akka.actor.ActorRef) extends Actor {

  import akka.actor.SupervisorStrategy.{ Restart, Resume, Stop }
  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException      => Resume
      case _: NullPointerException     => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception => {
        println("error happen .    resume worker")
        //        System.exit(1)
        //    	  Escalate
        Restart
      }

    }

  val workerRouter = context.actorOf(Props[DataNodeManager].withRouter(RoundRobinPool(nodeManagerNumber)), name = "workerRouter")
  def receive = {
    case Message(t, msg) =>
      println(s"${this.getClass}  message:  '$msg'")

    case td: TaskDesc => {
      workerRouter ! td
    }

    //TODO
    case _ => {
      println("Master unhandle")

    }

  }

}  