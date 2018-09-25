package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.aggregate.AggregateRoot
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Cottage internal constructor(
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campEditionId")))
        val campEditionId: CampEditionId
) : AggregateRoot<CottageId, CottageEvents>(CottageId())