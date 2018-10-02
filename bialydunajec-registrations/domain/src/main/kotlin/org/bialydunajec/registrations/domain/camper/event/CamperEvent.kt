package org.bialydunajec.registrations.domain.camper.event

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.camper.CamperId

sealed class CamperEvent : DomainEvent<CamperId> {
}