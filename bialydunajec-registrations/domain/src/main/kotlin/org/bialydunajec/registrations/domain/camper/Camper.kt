package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Pesel
import org.bialydunajec.registrations.domain.camper.event.CamperEvent
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.*

@Entity
class Camper internal constructor(
        @Embedded
        var pesel: Pesel? = null,
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        val cottageId: CottageId
) : AggregateRoot<CamperId, CamperEvent>(CamperId(pesel))