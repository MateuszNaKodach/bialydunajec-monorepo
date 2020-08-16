package org.bialydunajec.ddd.application.base.time

import java.time.ZonedDateTime

interface Clock {

    fun currentDateTime(): ZonedDateTime
    /*fun getCurrentInstant(): Instant
    fun getCurrentLocalDateTime(): LocalDateTime
    fun getCurrentZonedDateTime(): ZonedDateTime
    fun getCurrentLocalTime(): LocalTime*/
}