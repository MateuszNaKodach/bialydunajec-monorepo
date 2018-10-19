package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.valueobject.*
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

//TODO: Add address and geolocation!
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
        private var buildingPhotoUrl: Url? = null,

        @Embedded
        private var place: Place? = null,

        @Embedded
        private var cottageSpace: CottageSpace = CottageSpace(),

        @Embedded
        private var campersLimitations: CampersLimitations? = null,

        @Embedded
        private var bankTransferDetails: BankTransferDetails? = null
) : AggregateRoot<CottageId, CottageEvents>(
        when (cottageType) {
            CottageType.STANDALONE -> CottageId.ofStandaloneCottage(campRegistrationsEditionId)
            CottageType.ACADEMIC_MINISTRY -> CottageId.ofAcademicMinistryCottage(campRegistrationsEditionId, academicMinistryId
                    ?: throw DomainRuleViolationException.of(NO_DEFINED_ACADEMIC_MINISTRY_FOR_COTTAGE))
        }
), Versioned {

    @Version
    private var version: Long? = null

    @Enumerated(EnumType.STRING)
    private var status: CottageStatus = CottageStatus.UNCONFIGURED

    @Embedded
    private var cottageBoss: CottageBoss? = null

    fun update(
            name: String,
            logoImageUrl: Url?,
            buildingPhotoUrl: Url?,
            place: Place?,
            cottageSpace: CottageSpace,
            campersLimitations: CampersLimitations?,
            bankTransferDetails: BankTransferDetails?
    ) {
        if (this.name != name) {
            this.name = name
        }
        if (this.logoImageUrl != logoImageUrl) {
            canUpdateLogoImageUrl(logoImageUrl)
                    .ifInvalidThrowException()
            this.logoImageUrl = logoImageUrl
        }
        if (this.buildingPhotoUrl != buildingPhotoUrl) {
            this.buildingPhotoUrl = buildingPhotoUrl
        }
        if (this.place != place) {
            canUpdatePlace(place)
                    .ifInvalidThrowException()
            this.place = place
        }
        if (this.cottageSpace != cottageSpace) {
            this.cottageSpace = cottageSpace
        }
        if (this.campersLimitations != campersLimitations) {
            this.campersLimitations = campersLimitations
        }
        if (this.bankTransferDetails != bankTransferDetails) {
            canUpdateBankTransferDetails(bankTransferDetails)
                    .ifInvalidThrowException()
            this.bankTransferDetails = bankTransferDetails
        }
        updateConfigurationStatus()

    }

    fun updateName(name: String) {
        this.name = name
    }

    fun canUpdateLogoImageUrl(logoImageUrl: Url?) =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            ACTIVATED_COTTAGE_HAS_TO_HAVE_ADDRESS,
                            status == CottageStatus.ACTIVATED && logoImageUrl == null)
                    .toValidationResult()


    fun updateLogoImageUrl(logoImageUrl: Url?) {
        canUpdateLogoImageUrl(logoImageUrl)
                .ifInvalidThrowException()

        this.logoImageUrl = logoImageUrl
        updateConfigurationStatus()
    }

    fun updateBuildingPhotoUrl(buildingPhotoUrl: Url?) {
        this.buildingPhotoUrl = buildingPhotoUrl
    }

    fun canUpdatePlace(place: Place?) =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            ACTIVATED_COTTAGE_HAS_TO_HAVE_ADDRESS,
                            status == CottageStatus.ACTIVATED && (place == null || place?.address == null || place?.address?.city == null || place?.address?.street == null))
                    .toValidationResult()

    fun updatePlace(place: Place?) {
        canUpdatePlace(place)
                .ifInvalidThrowException()
        this.place = place
        updateConfigurationStatus()
    }

    fun updateCottageSpace(cottageSpace: CottageSpace) {
        this.cottageSpace = cottageSpace
        updateConfigurationStatus()
    }

    fun updateCampersLimitations(campersLimitations: CampersLimitations?) {
        this.campersLimitations = campersLimitations
    }

    fun canUpdateBankTransferDetails(bankTransferDetails: BankTransferDetails?) =
            ValidationResult.buffer()
                    .addViolatedRuleIf(
                            ACTIVATED_COTTAGE_HAS_TO_HAVE_BASIC_BANK_TRANSFER_INFO,
                            status == CottageStatus.ACTIVATED && (bankTransferDetails?.accountNumber == null || bankTransferDetails.accountOwner == null || bankTransferDetails.transferTitleTemplate == null))
                    .toValidationResult()


    fun updateBankTransferDetails(bankTransferDetails: BankTransferDetails?) {
        canUpdateBankTransferDetails(bankTransferDetails)
                .ifInvalidThrowException()

        this.bankTransferDetails = bankTransferDetails
        updateConfigurationStatus()
    }

    private fun updateConfigurationStatus() {
        val hasRequiredConfiguration =
                bankTransferDetails != null && !bankTransferDetails?.accountNumber.isNullOrBlank() && !bankTransferDetails?.accountOwner.isNullOrBlank() && !bankTransferDetails?.transferTitleTemplate.isNullOrBlank()
                        && logoImageUrl != null
                        && cottageSpace != null && cottageSpace?.fullCapacity != null && cottageSpace?.reservations != null
                        && place?.address != null && place?.address?.street != null && place?.address?.city != null

        if (hasRequiredConfiguration && this.status != CottageStatus.CONFIGURED && this.status != CottageStatus.ACTIVATED) {
            this.status = CottageStatus.CONFIGURED
            registerEvent(CottageEvents.CottageStatusChanged(getAggregateId(), status))
        } else if (!hasRequiredConfiguration) {
            this.status = CottageStatus.UNCONFIGURED
            registerEvent(CottageEvents.CottageStatusChanged(getAggregateId(), status))
        }
    }

    fun canActivate() =
            ValidationResult.buffer()
                    .addViolatedRuleIf(NOT_CONFIGURED_COTTAGE_CANNOT_BE_ACTIVATED, status != CottageStatus.CONFIGURED)
                    .toValidationResult()

    fun activate() {
        canActivate().ifInvalidThrowException()
        this.status = CottageStatus.ACTIVATED
    }

    fun isActivated() = this.status == CottageStatus.ACTIVATED

    fun deactivate() {
        this.status = CottageStatus.CONFIGURED
    }

    fun getCampEditionId() = campRegistrationsEditionId
    fun getCottageType() = cottageType
    fun getName() = name
    fun getLogoImageUrl() = logoImageUrl
    fun getBuildingPhotoUrl() = buildingPhotoUrl
    fun getPlace() = place
    fun getCottageSpace() = cottageSpace
    fun getCampersLimitations() = campersLimitations
    fun getBankTransferDetails() = bankTransferDetails
    fun getCottageState() = status
    fun getSnapshot() =
            CottageSnapshot(
                    getAggregateId(),
                    campRegistrationsEditionId,
                    cottageType,
                    academicMinistryId,
                    name,
                    logoImageUrl,
                    buildingPhotoUrl,
                    place,
                    cottageSpace,
                    campersLimitations,
                    bankTransferDetails,
                    status
            )

    override fun getVersion() = version

}