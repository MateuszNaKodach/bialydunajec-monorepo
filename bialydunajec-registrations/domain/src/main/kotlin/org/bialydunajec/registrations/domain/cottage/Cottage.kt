package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Url
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.valueobject.BankTransferDetails
import org.bialydunajec.registrations.domain.cottageaccommodation.CottageAccommodation
import javax.persistence.*

@Entity
class Cottage internal constructor(
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsId")))
        val campRegistrationsId: CampRegistrationsId,
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "academicMinistryId")))
        val academicMinistryId: AcademicMinistryId,
        val name: String,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "logoImageUrl")))
        val logoImageUrl: Url.InternalUrl? = null,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "buildingPhotoUrl")))
        val buildingPhotoUrl: Url.InternalUrl? = null,

        @Embedded
        val bankTransferDetails: BankTransferDetails? = null
) : AggregateRoot<CottageId, CottageEvents>(CottageId(campRegistrationsId, academicMinistryId)) {

    fun createCottageAccommodation(): CottageAccommodation {
        return CottageAccommodation(cottageId = getAggregateId(), campRegistrationsId = campRegistrationsId)
    }
}