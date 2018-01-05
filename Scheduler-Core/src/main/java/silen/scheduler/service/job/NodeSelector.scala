package silen.scheduler.service.job

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Actor

object NodeSelector {

  val system = ActorSystem("RootSystem")


  def findNewNode(acterClass: Class[_ <: Actor]) = {

    val acter = system.actorOf(Props(acterClass.newInstance()))

    acter

  }
  
  def findExistNode() = {
    
    
  }
  
}