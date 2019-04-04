package com.hype.utility

import org.joda.time.DateTime
import java.time.LocalDateTime

fun convertToDateTime(localDateTime: LocalDateTime): DateTime =
    DateTime(
        localDateTime.year,
        localDateTime.monthValue,
        localDateTime.dayOfMonth,
        localDateTime.hour,
        localDateTime.minute,
        localDateTime.second
    )

fun convertToLocalDateTime(dateTime: DateTime): LocalDateTime =
    LocalDateTime.of(
        dateTime.year,
        dateTime.monthOfYear,
        dateTime.dayOfMonth,
        dateTime.hourOfDay,
        dateTime.minuteOfHour,
        dateTime.secondOfMinute
    )
