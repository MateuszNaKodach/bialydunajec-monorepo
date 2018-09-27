package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Url
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.valueobject.BankTransferDetails
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Cottage internal constructor(
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsId")))
        val campRegistrationsId: CampRegistrationsId,

        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "academicMinistryId")))
        val academicMinistryId: AcademicMinistryId,

        @NotBlank
        var name: String,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "logoImageUrl")))
        var logoImageUrl: Url? = null,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "buildingPhotoUrl")))
        val buildingPhotoUrl: Url? = null,

        @Embedded
        var bankTransferDetails: BankTransferDetails? = null,

        @Embedded
        var cottageSpace: CottageSpace = CottageSpace()
) : AggregateRoot<CottageId, CottageEvents>(CottageId(campRegistrationsId, academicMinistryId)) {

}