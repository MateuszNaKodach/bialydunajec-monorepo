package org.bialydunajec.registrations.domain.camper.campparticipant

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipantSnapshot
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication

sealed class CampParticipantEvent : DomainEvent<CampParticipantId> {
    data class Registered(
            override val aggregateId: CampParticipantId,
            val snapshot: CampParticipantSnapshot
    ) : CampParticipantEvent()

    data class Confirmed(
            override val aggregateId: CampParticipantId,
            val snapshot: CampParticipantSnapshot
    ) : CampParticipantEvent()

    data class CampParticipantDataCorrected(
            override val aggregateId: CampParticipantId,
            val oldCamperData: CamperApplication,
            val newCamperData: CamperApplication
    ) : CampParticipantEvent()

    data class UnregisteredByAuthorized(
            override val aggregateId: CampParticipantId,
            val snapshot: CampParticipantSnapshot
    ) : CampParticipantEvent()
}
