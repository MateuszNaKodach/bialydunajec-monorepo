package org.bialydunajec.registrations.domain.camper.campparticipantregistration

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipantRegistrationSnapshot

sealed class CampParticipantRegistrationEvent : DomainEvent<CampParticipantRegistrationId> {
    data class Created(
            override val aggregateId: CampParticipantRegistrationId,
            val snapshot: CampParticipantRegistrationSnapshot
    ) : CampParticipantRegistrationEvent()

    data class Cancelled(
            override val aggregateId: CampParticipantRegistrationId) : CampParticipantRegistrationEvent()

    data class VerifiedByCamper(
            override val aggregateId: CampParticipantRegistrationId,
            val snapshot: CampParticipantRegistrationSnapshot
    ) : CampParticipantRegistrationEvent()
}