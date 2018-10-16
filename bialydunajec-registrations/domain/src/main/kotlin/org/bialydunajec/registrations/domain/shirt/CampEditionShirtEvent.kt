package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrationsId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings

sealed class CampEditionShirtEvent(
        override val aggregateId: CampEditionShirtId
) : DomainEvent<CampEditionShirtId> {

}