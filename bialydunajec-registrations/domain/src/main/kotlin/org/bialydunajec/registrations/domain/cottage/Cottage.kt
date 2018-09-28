package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.exception.DomainException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.camper.Camper
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.valueobject.*
import org.bialydunajec.registrations.domain.exception.CampersRegisterDomainErrorCode
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
//@Table(schema = "camp_registrations")
class Cottage internal constructor(

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsId")))
        private val campRegistrationsId: CampRegistrationsId,

        @NotNull
        @Enumerated(EnumType.STRING)
        private val cottageType: CottageType,

        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "academicMinistryId")))
        private val academicMinistryId: AcademicMinistryId?,

        @NotBlank
        private var name: String,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "logoImageUrl")))
        private var logoImageUrl: Url? = null,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "buildingPhotoUrl")))
        private val buildingPhotoUrl: Url? = null,

        @Embedded
        private var cottageSpace: CottageSpace = CottageSpace(),

        @Embedded
        private var campersLimitations: CampersLimitations? = CampersLimitations(),

        @Embedded
        private var bankTransferDetails: BankTransferDetails? = null
) : AggregateRoot<CottageId, CottageEvents>(
        when (cottageType) {
            CottageType.STANDALONE -> CottageId.ofStandaloneCottage(campRegistrationsId)
            CottageType.ACADEMIC_MINISTRY -> CottageId.ofAcademicMinistryCottage(campRegistrationsId, academicMinistryId
                    ?: throw DomainException.of(CampersRegisterDomainErrorCode.NO_DEFINED_ACADEMIC_MINISTRY_FOR_COTTAGE))
        }
) {

    @NotBlank
    @Enumerated(EnumType.STRING)
    private var cottageState: CottageState = CottageState.UNCONFIGURED

    fun getCampRegistrationsId() = campRegistrationsId
    fun getCottageType() = cottageType
    fun getName() = name
    fun getLogoImageUrl() = logoImageUrl
    fun getBuildingPhotoUrl() = buildingPhotoUrl
    fun getCottageSpace() = cottageSpace
    fun getCampersLimitations() = campersLimitations
    fun getBankTransferDetails() = bankTransferDetails
    fun getCottageState() = cottageState

}