package org.bialydunajec.ddd.domain.sharedkernel.valueobject.time

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import javax.persistence.Embeddable
import javax.validation.constraints.Positive


@Embeddable
class ZonedDateTimeRange(
        val start: ZonedDateTime? = null,
        val end: ZonedDateTime? = null
) : ValueObject {
    fun getStartDate() = start
    fun getStartDateTime() = start?.toOffsetDateTime()?.toOffsetTime()
    fun getEndDate() = end
    fun getEndDateTime() = end?.toOffsetDateTime()?.toOffsetTime()
}