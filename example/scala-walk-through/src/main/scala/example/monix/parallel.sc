// On evaluation a Scheduler is needed
import monix.execution.Scheduler.Implicits.global
import org.atnos.eff.{Eff, Fx}

import scala.collection.mutable.ListBuffer
// For Task
import monix.eval._
// For Observable
import monix.reactive._

//val items = 0 until 1000
//
//// The list of all tasks needed for execution
//val tasks = items.map(i => Task(i * 2))
//// Processing in parallel
//val aggregate = Task.gather(tasks).map(x=>x.toList)
//
//// Evaluation:
//aggregate.foreach(println)
////=> List(0, 2, 4, 6, 8, 10, 12, 14, 16,...



val items2 = 0 until 30
val l2 = items2.toList
// The list of all tasks needed for execution
val tasks2 = l2.map(i => Task(i * 2))
// Building batches of 10 tasks to execute in parallel:
val batches = tasks2.sliding(10,10).map(b => Task.gather(b)).toIterable
// Sequencing batches, then flattening the final result
val aggregate2 = Task.sequence(batches).map(_.flatten.toList)

// Evaluation:
aggregate2.foreach(println)


// Eff

import cats.implicits._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._
import org.specs2._
import org.specs2.concurrent.ExecutionEnv

import scala.collection.mutable.ListBuffer
import monix.execution.Scheduler.Implicits.global
import monix.eval.Task
import org.atnos.eff._
import org.atnos.eff.addon.monix.task._
import org.atnos.eff.syntax.addon.monix.task._

import scala.concurrent.Await
import scala.concurrent.duration._

type S = Fx.fx2[Task, Option]

val messages: ListBuffer[Int] = new ListBuffer[Int]
val delays = List(600, 200, 400, 800)

def action(i: Int): Eff[S, Unit] =
  taskFork(Task.delay {
    Thread.sleep(i.toLong)
    messages.append(i)
  })

val run = taskDelay[S, Unit](Thread.sleep(1000)) >> Eff.traverseA(delays)(action)


val result = Await.result(run.runOption.runAsync.runToFuture, 3.seconds)

println(result)

