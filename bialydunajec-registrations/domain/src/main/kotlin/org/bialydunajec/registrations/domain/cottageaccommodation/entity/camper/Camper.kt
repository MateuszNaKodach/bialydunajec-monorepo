package org.bialydunajec.registrations.domain.cottageaccommodation.entity.camper

import org.bialydunajec.ddd.domain.base.persistence.AuditableEntity
import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Pesel
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.hibernate.validator.constraints.pl.PESEL
import javax.persistence.*

@Entity
internal class Camper internal constructor(
        @Embedded
        var pesel: Pesel? = null,
        @EmbeddedId
        override val entityId: CamperId = CamperId(pesel),
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        val cottageId: CottageId
) : AuditableEntity(), IdentifiedEntity<CamperId>