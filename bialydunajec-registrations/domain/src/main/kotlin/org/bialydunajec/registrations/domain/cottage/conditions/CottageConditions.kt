package org.bialydunajec.registrations.domain.cottage.conditions

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.cottage.CottageId

class CottageConditions(cottageId: CottageId) : AggregateRoot<CottageId, DomainEvent<CottageId>>(cottageId) {

    var items: List<CottageConditionsDescriptionItem> = listOf()
        private set


    fun update(newConditionsDescription: List<CottageConditionsDescriptionItem>) {
        items = newConditionsDescription
    }
}
