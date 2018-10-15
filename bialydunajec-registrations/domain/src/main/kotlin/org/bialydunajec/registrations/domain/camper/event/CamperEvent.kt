package org.bialydunajec.registrations.domain.camper.event

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.camper.CampParticipantId

sealed class CamperEvent : DomainEvent<CampParticipantId> {
}