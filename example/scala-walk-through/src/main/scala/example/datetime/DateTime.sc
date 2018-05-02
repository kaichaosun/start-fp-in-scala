//import java.time.{Duration, Instant, LocalDate, LocalDateTime}
//
//
////val now = Instant.now
//
//
//val localDate = LocalDate.now
//
//localDate.getDayOfMonth
//
//localDate.getMonth
//
//localDate.minusDays(1)
//
//val localDateTime = LocalDateTime.now()

import java.time.{Month, ZoneId}

import com.github.nscala_time.time.Imports._

DateTime.now() + 2.months


val now = DateTime.now()

val currentMonth = DateTime.now().getMonthOfYear

val quarter = if (currentMonth <= 3) {
  1
} else if (currentMonth <= 6) {
  2
} else if (currentMonth <= 9) {
  3
} else {
  4
}
val currentYear = now.getYear

now.withMonth(1).withDayOfMonth(1).withMillisOfDay(0)
now.withMonth(1).withDayOfMonth(1).withMillisOfDay(0).toInstant

now.withMonth(4).withDayOfMonth(1).withMillisOfDay(0)

2 match {
  case 1 | 2 => "first"
}

now.withYear(now.getYear - 1).withMonth(10).withDayOfMonth(1).withMillisOfDay(0)

now.withMonth(1).withDayOfMonth(1).withMillisOfDay(0) - 1.seconds


(now.withMonth(1).withDayOfMonth(1).withMillisOfDay(0) - 1.seconds).getMonthOfYear

now.getMonthOfYear
now.monthOfYear()


val month = now.getMonthOfYear

if (month <= 3) {
  (
    now.withYear(now.getYear - 1).withMonth(10).withDayOfMonth(1).withMillisOfDay(0),
    now.withMonth(1).withDayOfMonth(1).withMillisOfDay(0) - 1.seconds
  )
} else if (month <= 6) {
  (
    now.withMonth(1).withDayOfMonth(1).withMillisOfDay(0),
    now.withMonth(4).withDayOfMonth(1).withMillisOfDay(0) - 1.seconds
  )
} else if (month <= 9) {
  (
    now.withMonth(4).withDayOfMonth(1).withMillisOfDay(0),
    now.withMonth(7).withDayOfMonth(1).withMillisOfDay(0) - 1.seconds
  )
} else {
  (
    now.withMonth(7).withDayOfMonth(1).withMillisOfDay(0),
    now.withMonth(10).withDayOfMonth(1).withMillisOfDay(0) - 1.seconds
  )
}

now.withMonth(1).withDayOfMonth(1).withMillisOfDay(0).toString

import java.time.ZonedDateTime

val zoneNow = ZonedDateTime.now

zoneNow.getMonth
zoneNow.getMonthValue

zoneNow.getYear

zoneNow.withMonth(7).withDayOfMonth(1).withSecond(0)



java.time.LocalDateTime.now

