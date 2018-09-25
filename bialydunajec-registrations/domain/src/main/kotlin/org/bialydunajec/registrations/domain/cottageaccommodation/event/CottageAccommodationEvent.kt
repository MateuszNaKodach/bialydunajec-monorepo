package org.bialydunajec.registrations.domain.cottageaccommodation.event

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.cottageaccommodation.CottageAccommodationId

sealed class CottageAccommodationEvent : DomainEvent<CottageAccommodationId> {
}