package com.defender

import akka.actor.SupervisorStrategy._
import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, OneForOneStrategy, Props }
import akka.pattern.ask
import akka.util.Timeout
import com.defender.SupervisorActor.{ AddSupervisedActorRequest, AddSupervisedActorResponse, _ }

import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, Future }

class Supervisor(implicit system: ActorSystem, executor: ExecutionContext, timeout: Timeout) {
  val supervisor: ActorRef = system.actorOf(SupervisorActor.props, "supervisor")

  def add(p: Props, id: String): Future[ActorRef] =
    (supervisor ? AddSupervisedActorRequest(p, id))
      .mapTo[AddSupervisedActorResponse]
      .map(_.a)
}

class SupervisorActor extends Actor with ActorLogging {
  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy(maxNrOfRetries = MaxNrOfRetries, withinTimeRange = 1 minute) {
      case e: Exception =>
        log.error(e.getMessage, e)
        Restart
    }
  def receive: PartialFunction[Any, Unit] = {
    case AddSupervisedActorRequest(p, id) =>
      sender() ! AddSupervisedActorResponse(context.actorOf(p, id))
  }
}

object SupervisorActor {
  val MaxNrOfRetries = 10

  def props: Props = Props(new SupervisorActor)

  case class AddSupervisedActorRequest(p: Props, id: String)
  case class AddSupervisedActorResponse(a: ActorRef)
}