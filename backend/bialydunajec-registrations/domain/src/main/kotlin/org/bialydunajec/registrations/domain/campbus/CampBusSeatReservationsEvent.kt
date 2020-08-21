package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusSeatReservationsId

sealed class CampBusSeatReservationsEvent : DomainEvent<CampBusSeatReservationsId>{
}