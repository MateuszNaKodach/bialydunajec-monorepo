package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusLineId

sealed class CampBusEvent : DomainEvent<CampBusLineId>{
    data class Created(override val aggregateId: CampBusLineId) : CampBusEvent()
}