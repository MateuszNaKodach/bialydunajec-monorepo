package org.bialydunajec.ddd.domain.extensions

import java.time.LocalDate

fun LocalDate.isBetween(start: LocalDate, end: LocalDate) = this.isAfter(start) && this.isBefore(end)