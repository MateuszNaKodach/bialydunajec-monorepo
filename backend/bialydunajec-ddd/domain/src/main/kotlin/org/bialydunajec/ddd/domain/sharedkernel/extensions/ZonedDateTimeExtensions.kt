package org.bialydunajec.ddd.domain.sharedkernel.extensions

import java.time.ZonedDateTime

fun ZonedDateTime.isBetween(start: ZonedDateTime, end: ZonedDateTime) = this.isAfter(start) && this.isBefore(end)
