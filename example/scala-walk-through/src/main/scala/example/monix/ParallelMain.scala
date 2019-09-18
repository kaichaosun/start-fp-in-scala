package example.monix

import cats.effect.IO
import cats.implicits._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._
import org.specs2._
import org.specs2.concurrent.ExecutionEnv

import scala.collection.mutable.ListBuffer
import monix.execution.Scheduler.Implicits.global
import monix.eval.Task
import monix.execution.Scheduler
import org.atnos.eff._
import org.atnos.eff.addon.cats.effect.IOEffect
import org.atnos.eff.addon.monix.task._
import org.atnos.eff.syntax.addon.monix.task._

import scala.concurrent.Await
import scala.concurrent.duration._

object ParallelMain extends App {
//  type S = Fx.fx2[Task, Option]
//
//  val messages: ListBuffer[Int] = new ListBuffer[Int]
//  val delays = List(600, 200, 400, 800)
//
//  def action(i: Int): Eff[S, Unit] =
//    taskFork(Task.delay{
//      Thread.sleep((i * 10).toLong)
//      println(s"I'm in $i")
//      messages.append(i)
//    })
//
//  val run = taskDelay[S, Unit](Thread.sleep(1000)) >> Eff.traverseA(delays)(action)
//
//
//  val result = Await.result(run.runOption.runAsync.runToFuture, 20.seconds)
//
//  println(result)
//
//  println(messages)


  type R = Fx.fx1[Option]
//
//  def loop(i: Int): Task[Eff[R, Int]] =
//    if (i == 0) Task.now(Eff.pure(1))
//    else Task.now(taskSuspend(loop(i - 1)).map(_ + 1))
//
//  Await.result(taskSuspend(loop(100000)).runSequential.runToFuture, Duration.Inf)

  val source = (0 until 100).toList
  def fin(i: Int): Eff[R, Int] =
    Eff.pure(i+10)

//  val res1 = source.map(i => {
//    taskSuspend(Task.now(fin(i)))
//  }).sequence.runAsync

  val res2 = source.map(i => {
    Task.delay{
      println(s"I'm in second ${Thread.currentThread().getName} $i")
      fin(i)
    }
  })

  val batch2 = res2.sliding(10, 10).map(b => Task.gather(b)).toIterable
  val aggre2 = Task.sequence(batch2).map(_.flatten.toList).map(_.sequence)
//  aggre2.foreach(eff => eff.runAsync.runToFuture.foreach(x=>println(x)))

  val resultf = Await.result(aggre2.runToFuture(Scheduler(ExecutorServices.executor(10))), Duration.Inf).runOption.run
  println(resultf)


//  val items2 = 0 until 30
//  val l2 = items2.toList
//  // The list of all tasks needed for execution
//  val tasks2 = l2.map(i => Task(i * 2))
//  // Building batches of 10 tasks to execute in parallel:
//  val batches = tasks2.sliding(10,10).map(b => Task.gather(b)).toIterable
//  // Sequencing batches, then flattening the final result
//  val aggregate2 = Task.sequence(batches).map(_.flatten.toList)
//
//// Evaluation:
//  aggregate2.foreach(println)

}
