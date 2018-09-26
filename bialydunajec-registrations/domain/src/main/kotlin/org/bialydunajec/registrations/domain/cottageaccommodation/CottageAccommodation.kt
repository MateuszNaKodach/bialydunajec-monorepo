package org.bialydunajec.registrations.domain.cottageaccommodation

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Pesel
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottageaccommodation.entity.camper.Camper
import org.bialydunajec.registrations.domain.cottageaccommodation.event.CottageAccommodationEvent
import javax.persistence.*

@Entity
class CottageAccommodation internal constructor(
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        val cottageId: CottageId,
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsId")))
        val campRegistrationsId: CampRegistrationsId
) : AggregateRoot<CottageAccommodationId, CottageAccommodationEvent>(CottageAccommodationId(campRegistrationsId, cottageId)) {

    @OneToMany
    private val accommodatedCampers = mutableListOf<Camper>()

    //fun getAccommodatedCamperSnapshotByPesel(pesel: Pesel): CamperSnapshot? = null
}