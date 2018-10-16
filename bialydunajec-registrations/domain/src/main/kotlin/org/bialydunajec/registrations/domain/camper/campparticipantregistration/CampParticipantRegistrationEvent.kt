package org.bialydunajec.registrations.domain.camper.campparticipantregistration

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication

sealed class CampParticipantRegistrationEvent : DomainEvent<CampParticipantRegistrationId> {
    data class CampParticipantRegistrationCreated(
            override val aggregateId: CampParticipantRegistrationId,
            val camperApplication: CamperApplication
    ) : CampParticipantRegistrationEvent()

    data class CampParticipantRegistrationCancelled(
            override val aggregateId: CampParticipantRegistrationId) : CampParticipantRegistrationEvent()
}