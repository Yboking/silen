import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.OneForOneStrategy
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.routing.RoundRobinPool
import youe.data.service.job.DataNodeManager
import youe.data.service.job.data.Message

object MasterSystem extends App {
  val system = ActorSystem("MasterSystem")
  val remoteActor = system.actorOf(Props(new JobScheduleListener(1, null)), name = "DataNodeManager")
  
  // Test Alive 
    remoteActor ! Message (content = "The RemoteActor is alive")

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



    case str: String => {

      workerRouter.!(str)(sender)

    }

    //TODO
    case _ => {
      println("Master unhandle")
      
      
    }

  }

}  