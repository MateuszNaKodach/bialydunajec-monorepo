package org.bialydunajec.ddd.domain.sharedkernel.valueobject.time

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import org.bialydunajec.ddd.domain.sharedkernel.extensions.isBetween
import java.time.LocalDate
import javax.persistence.Embeddable


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
