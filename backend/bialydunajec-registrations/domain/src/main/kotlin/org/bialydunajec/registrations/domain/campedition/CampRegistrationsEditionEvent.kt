package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrationsId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings

sealed class CampRegistrationsEditionEvent(
        override val aggregateId: CampRegistrationsEditionId
) : DomainEvent<CampRegistrationsEditionId> {
    class CampRegistrationsCreated(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campRegistrationsId: CampRegistrationsId,
            val timerSettings: TimerSettings?
    ) : CampRegistrationsEditionEvent(campRegistrationsEditionId)

    class CampRegistrationsStarted(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campRegistrationsId: CampRegistrationsId,
            val timerSettings: TimerSettings?
    ) : CampRegistrationsEditionEvent(campRegistrationsEditionId)

    class CampRegistrationsFinished(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campRegistrationsId: CampRegistrationsId
    ) : CampRegistrationsEditionEvent(campRegistrationsEditionId)

    class CampRegistrationsSuspended(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campRegistrationsId: CampRegistrationsId
    ) : CampRegistrationsEditionEvent(campRegistrationsEditionId)

    class CampRegistrationsUnsuspended(
            val campRegistrationsEditionId: CampRegistrationsEditionId,
            val campRegistrationsId: CampRegistrationsId
    ) : CampRegistrationsEditionEvent(campRegistrationsEditionId)
}