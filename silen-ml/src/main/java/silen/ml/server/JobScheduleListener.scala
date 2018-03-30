package silen.ml.server
import scala.concurrent.duration.DurationInt
import akka.actor.OneForOneStrategy
import akka.routing.RoundRobinPool
import akka.actor.Props
import akka.actor.Actor
import breeze.linalg._
import silen.ml.common.Param

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

    case p: Param => {
      handleParam(p)

    }

    case _ => {

    }
  }

  def handleParam(param: Param) {

  }
}