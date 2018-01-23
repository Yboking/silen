package silen.scheduler.service.client

import silen.scheduler.service.job.RootObserver
import silen.scheduler.data.job.NodeIdentity
import akka.actor.Actor
import silen.scheduler.service.job.MasterSystem
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import silen.scheduler.data.job.RenderData

class UIListener {

  val targetPath = "akka.tcp://UISystem@192.168.30.44:9527/user/UIManager"

  def update(ndi: NodeIdentity) {

  }
  def handle(ndi: Any) {

    val target = MasterSystem.chooseActor(targetPath)

    if (ndi.isInstanceOf[NodeIdentity]) {

      val obj = ndi.asInstanceOf[NodeIdentity]

      println("UIListener : >> Node Name" + obj.getName())
      target ! (s"Node ${obj.id} started: ${obj.getName()} ")

    }

    if (ndi.isInstanceOf[RenderData]) {
      val obj = ndi.asInstanceOf[RenderData]
      target ! obj.toTuple
    }

  }

}

object Main {

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