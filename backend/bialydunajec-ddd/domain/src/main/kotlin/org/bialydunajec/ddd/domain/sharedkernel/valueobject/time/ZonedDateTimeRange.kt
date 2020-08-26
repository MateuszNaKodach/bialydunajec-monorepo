package org.bialydunajec.ddd.domain.sharedkernel.valueobject.time

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import org.bialydunajec.ddd.domain.sharedkernel.extensions.isBetween
import org.bialydunajec.ddd.domain.sharedkernel.exception.SharedKernelDomainError
import java.time.ZonedDateTime
import javax.persistence.Embeddable

@Embeddable
class ZonedDateTimeRange(
        val start: ZonedDateTime,
        val end: ZonedDateTime
) : ValueObject {

    init {
        if (end.isBefore(start)) {
            throw DomainRuleViolationException.of(SharedKernelDomainError.END_DATE_CANNOT_BE_BEFORE_START_DATE)
        }
    }

    fun getStartDate() = start
    fun getStartDateTime() = start.toOffsetDateTime()?.toOffsetTime()
    fun getEndDate() = end
    fun getEndDateTime() = end.toOffsetDateTime()?.toOffsetTime()

    fun overlapWith(zonedDateTimeRange: ZonedDateTimeRange) =
            start.isBetween(zonedDateTimeRange.start, zonedDateTimeRange.end)
                    || end.isBetween(zonedDateTimeRange.start, zonedDateTimeRange.end)
                    || includes(zonedDateTimeRange)

    fun includes(zonedDateTimeRange: ZonedDateTimeRange) =
            start.isBefore(zonedDateTimeRange.start) && end.isAfter(zonedDateTimeRange.end)


}
