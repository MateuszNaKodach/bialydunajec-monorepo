package org.bialydunajec.registrations.domain.cottageaccommodation

import org.bialydunajec.ddd.domain.aggregate.AggregateRoot
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.*

@Entity
class CottageAccommodation internal constructor(
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        val cottageId: CottageId
) : AggregateRoot<CottageAccommodationId, CottageAccommodationEvents>(CottageAccommodationId()) {
}