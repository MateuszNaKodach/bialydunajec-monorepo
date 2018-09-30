package org.bialydunajec.registrations.domain.campedition.valueobject

import java.time.ZonedDateTime
import javax.persistence.Embeddable

@Embeddable
data class TimerSettings(
        val startDate: ZonedDateTime? = null,
        val endDate: ZonedDateTime? = null
)