package org.bialydunajec.ddd.domain.sharedkernel.valueobject.time

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.bialydunajec.ddd.domain.extensions.isBetween
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import javax.persistence.Embeddable
import javax.validation.constraints.Positive


@Embeddable
class LocalDateRange(
        val start: LocalDate,
        val end: LocalDate
) : ValueObject {
    fun getStartDate() = start
    fun getEndDate() = end

    fun overlapWith(localDateRange: LocalDateRange) =
            start.isBetween(localDateRange.start, localDateRange.end)
                    || end.isBetween(localDateRange.start, localDateRange.end)
                    || includes(localDateRange)

    fun includes(localDateRange: LocalDateRange) =
            start.isBefore(localDateRange.start) && end.isAfter(localDateRange.end)
}