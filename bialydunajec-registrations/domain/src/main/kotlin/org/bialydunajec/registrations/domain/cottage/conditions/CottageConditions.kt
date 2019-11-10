package org.bialydunajec.registrations.domain.cottage.conditions

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(
    schema = "camp_registrations"
)
class CottageConditions(cottageId: CottageId) : AggregateRoot<CottageId, DomainEvent<CottageId>>(cottageId) {

    @ElementCollection
    var items: List<CottageConditionsDescriptionItem> = listOf()
        private set

    fun update(newConditionsDescription: List<CottageConditionsDescriptionItem>) {
        items = newConditionsDescription
    }
}
