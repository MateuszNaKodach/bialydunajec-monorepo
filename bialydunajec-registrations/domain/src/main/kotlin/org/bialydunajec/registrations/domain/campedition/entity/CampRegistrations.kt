package org.bialydunajec.registrations.domain.campedition.entity

import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.jetbrains.annotations.NotNull
import java.time.Instant
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
//@Table(schema = "camp_registrations")
internal class CampRegistrations constructor(
        campEditionId: CampEditionId,

        @NotNull
        var startDate: ZonedDateTime? = null,

        @NotNull
        var endDate: ZonedDateTime? = null
) : IdentifiedEntity<CampRegistrationsId> {

    @EmbeddedId
    override val entityId: CampRegistrationsId = CampRegistrationsId(campEditionId)

    internal fun isStarted(currentTime: ZonedDateTime) = currentTime.isAfter(startDate)

    internal fun isEnded(currentTime: ZonedDateTime) = currentTime.isAfter(endDate)
}