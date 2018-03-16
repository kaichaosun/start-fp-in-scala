import java.math.MathContext
import java.time.{Instant, ZoneOffset}
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.ChronoUnit
import java.util.Locale

import org.atnos.eff._
import all._
import syntax.all._

type S = Fx.fx1[List]

// create all the possible pairs for a given list
// where the sum is greater than a value
def pairsBiggerThan[R :_list](list: List[Int], n: Int): Eff[R, (Int, Int, Int)] = for {
  t <- Eff.pure(10)
  a <- values(list:_*)
  b <- values(list:_*)
  found <- if (a + b > n) singleton((10, a, b))
  else           empty
} yield found

pairsBiggerThan[S](List(1, 2, 3, 4), 5).runList.run




val a = BigDecimal(1.10, MathContext.DECIMAL32)

val i1 = Instant.now()

val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.UK).withZone(ZoneOffset.UTC)



formatter.format(i1)
i1.toString


val nowSeconds = Instant.ofEpochSecond(0L).until(Instant.now(),
  ChronoUnit.SECONDS);

Instant.ofEpochSecond(nowSeconds)

Thread.sleep(2000)


Instant.ofEpochSecond(Instant.now.getEpochSecond)

Instant.ofEpochSecond(Instant.now.getEpochSecond).plus(45.toLong, ChronoUnit.DAYS)




