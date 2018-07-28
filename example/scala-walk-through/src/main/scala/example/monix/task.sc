// We need a scheduler whenever asynchronous
// execution happens, substituting your ExecutionContext
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration._
import monix.eval._
import monix.reactive.Observable

// A specification for evaluating a sum,
// nothing gets triggered at this point!
val task = Task { 1 + 1 }

// Actual execution, making use of the Scheduler in
// our scope, imported above
val future = task.runAsync

Await.result(future, 10.seconds)

// Nothing happens here, as observable is lazily
// evaluated only when the subscription happens!
val tick = {
  Observable.interval(1.second)
    .filter(_ % 2 == 0)
    .map(_ * 2)
    .flatMap(x => Observable.fromIterable(Seq(x, x)))
    .take(5)
    .dump("Out")
}

// Execution happens here, after subscribe
val cancelable = tick.subscribe()
