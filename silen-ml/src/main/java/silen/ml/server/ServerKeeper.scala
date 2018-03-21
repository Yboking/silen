package silen.ml.server

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Actor
import akka.actor.OneForOneStrategy
import scala.concurrent.duration.DurationInt
import akka.routing.RoundRobinPool

object ServerKeeper extends App {

  //   val pro = new java.util.Properties()
  //  pro.setProperty("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
  //  pro.setProperty("akka.remote.transport", "akka.remote.netty.NettyRemoteTransport")
  //  pro.setProperty("akka.remote.netty.tcp.port", "9528")

  //  val config = ConfigFactory.parseProperties(pro)
  //  val system = ActorSystem("ServerKeeper", config)

  val system = ActorSystem("ServerKeeperSystem")

  val remoteActor = system.actorOf(Props(new JobScheduleListener(1, null)), name = "ServerKeeperManager")

}

 abstract class PServer extends Actor

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

  val workerRouter = context.actorOf(Props[PServer].withRouter(RoundRobinPool(nodeManagerNumber)), name = "workerRouter")
  def receive = {

    case _ => {

    }
  }

}