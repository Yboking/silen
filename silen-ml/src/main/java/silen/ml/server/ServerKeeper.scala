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

 