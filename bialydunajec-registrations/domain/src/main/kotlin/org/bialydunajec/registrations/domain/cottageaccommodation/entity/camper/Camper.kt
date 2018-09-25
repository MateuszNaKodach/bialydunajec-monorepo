package org.bialydunajec.registrations.domain.cottageaccommodation.entity.camper

import org.bialydunajec.ddd.domain.base.persistence.AuditableEntity
import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.*

@Entity
class Camper internal constructor(
        @EmbeddedId
        override val entityId: CamperId = CamperId(),
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        val cottageId: CottageId
) : AuditableEntity(), IdentifiedEntity<CamperId> {
}