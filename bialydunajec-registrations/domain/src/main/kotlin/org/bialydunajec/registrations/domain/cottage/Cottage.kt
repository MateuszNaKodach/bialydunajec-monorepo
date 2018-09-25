package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottageaccommodation.CottageAccommodation
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Cottage internal constructor(
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsId")))
        val campRegistrationsId: CampRegistrationsId
) : AggregateRoot<CottageId, CottageEvents>(CottageId()) {

    fun createCottageAccommodation(): CottageAccommodation {
        return CottageAccommodation(cottageId = getAggregateId(), campRegistrationsId = campRegistrationsId)
    }
}