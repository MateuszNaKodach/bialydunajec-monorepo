package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.valueobject.*
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(
        schema = "camp_registrations",
        uniqueConstraints = [
            UniqueConstraint(columnNames = arrayOf("campRegistrationsEditionId", "academicMinistryId"), name = "one_cottage_for_academic_ministry"),
            UniqueConstraint(columnNames = arrayOf("campRegistrationsEditionId", "name"), name = "unique_cottageName_on_edition")
        ]
)
class Cottage internal constructor(

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campRegistrationsEditionId")))
        private val campRegistrationsEditionId: CampRegistrationsEditionId,

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
            CottageType.STANDALONE -> CottageId.ofStandaloneCottage(campRegistrationsEditionId)
            CottageType.ACADEMIC_MINISTRY -> CottageId.ofAcademicMinistryCottage(campRegistrationsEditionId, academicMinistryId
                    ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.NO_DEFINED_ACADEMIC_MINISTRY_FOR_COTTAGE))
        }
) {

    @Enumerated(EnumType.STRING)
    private var cottageState: CottageState = CottageState.UNCONFIGURED

    fun getCampEditionId() = campRegistrationsEditionId
    fun getCottageType() = cottageType
    fun getName() = name
    fun getLogoImageUrl() = logoImageUrl
    fun getBuildingPhotoUrl() = buildingPhotoUrl
    fun getCottageSpace() = cottageSpace
    fun getCampersLimitations() = campersLimitations
    fun getBankTransferDetails() = bankTransferDetails
    fun getCottageState() = cottageState

}