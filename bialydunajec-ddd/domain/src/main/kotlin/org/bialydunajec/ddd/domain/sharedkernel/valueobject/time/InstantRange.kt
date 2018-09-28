package org.bialydunajec.ddd.domain.sharedkernel.valueobject.time

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.Embeddable
import javax.validation.constraints.Positive


@Embeddable
class InstantRange(
        val start: Instant? = null,
        val end: Instant? = null
) : ValueObject {
    fun getStartDate() = LocalDate.from(start)
    fun getStartDateTime() = LocalTime.from(end)
    fun getEndDate() = LocalDate.from(end)
    fun getEndDateTime() = LocalTime.from(end)
}