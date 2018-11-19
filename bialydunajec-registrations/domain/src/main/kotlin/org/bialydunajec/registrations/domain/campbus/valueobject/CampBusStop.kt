package org.bialydunajec.registrations.domain.campbus.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import java.time.Instant
import java.util.Objects.nonNull
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Lob

@Embeddable
data class CampBusStop(
        @Embedded
        val place: Place?,
        val date: Instant? = null,

        @Lob
        val notes: String? = null
) {
    fun isPlanned() = nonNull(place) && nonNull(date)
}