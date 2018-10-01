package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrationsId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings

sealed class CampEditionEvent(
        override val aggregateId: CampEditionId
) : DomainEvent<CampEditionId> {
    class CampRegistrationsStarted(
            val campEditionId: CampEditionId,
            val campRegistrationsId: CampRegistrationsId,
            val timerSettings: TimerSettings
    ) : CampEditionEvent(campEditionId)

    class CampRegistrationsFinished(
            val campEditionId: CampEditionId,
            val campRegistrationsId: CampRegistrationsId
    ) : CampEditionEvent(campEditionId)

    class CampRegistrationsSuspended(
            val campEditionId: CampEditionId,
            val campRegistrationsId: CampRegistrationsId
    ) : CampEditionEvent(campEditionId)
}