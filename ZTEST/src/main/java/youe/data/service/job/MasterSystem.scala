import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.OneForOneStrategy
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.routing.RoundRobinPool
import youe.data.work.Worker
import com.yc.value.WorkDesc
import youe.data.valuebeans.RequestBean
import com.yc.value.WorkType

object MasterSystem extends App {
  val system = ActorSystem("MasterSystem")
  val remoteActor = system.actorOf(Props(new RemoteActor(1, null)), name = "RemoteActor")
  remoteActor ! Message("The RemoteActor is alive")

  def start() {

    val system = ActorSystem("MasterSystem")
    val remoteActor = system.actorOf(Props(new RemoteActor(30, null)), name = "RemoteActor")
    remoteActor ! Message("The RemoteActor is alive")

  }
}

class RemoteActor(workNumber: Int, listener: akka.actor.ActorRef) extends Actor {

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

  val workerRouter = context.actorOf(Props[Worker].withRouter(RoundRobinPool(workNumber)), name = "workerRouter")
  val patentWorker = context.system.actorOf(Props[Worker], name = "patentWorker")
  def receive = {
    case Message(msg) =>
      println(s"RemoteActor received message '$msg'")
      //      sender ! DataResult(Array(Record("tom"), Record("lily")))
      workerRouter.!(Message(msg))(sender)

    case WorkDesc(wtype: Byte, arrgs: Map[String, Any]) =>


    case str: String => {

      workerRouter.!(str)(sender)

    }

    case rb: RequestBean => {

      workerRouter.!(rb)(sender)

    }
    //TODO
    case _ => println("Master unhandle")

  }

}  