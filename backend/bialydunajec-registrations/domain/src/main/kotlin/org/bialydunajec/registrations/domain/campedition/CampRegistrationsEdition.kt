package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.validation.ValidationResult
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.academicministry.CampRegistrationsAcademicMinistry
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrations
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageType
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.ZonedDateTime
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionEvent.*
import org.bialydunajec.registrations.domain.campedition.specification.CampRegistrationsCanStartSpecification
import org.bialydunajec.registrations.domain.campedition.valueobject.CampRegistrationsEditionSnapshot
import javax.persistence.*

//TODO: Aby rozpoczać lub skonfigurować zapisy cała reszta musi być finished!!!
//TODO: CampRegistrationsEdition musi miec jako entity CampRegistrations i dbac np. o daty, zeby rejestracja nie trwała dłużej niz koniec obozu!
/** Czy nie zmienic na CampEditionRegistrations --- lepsza nazwa?
 * Camp Edition in Camp Registrations Bounded Context
 */
@Entity
@Table(schema = "camp_registrations")
class CampRegistrationsEdition constructor(
        campRegistrationsEditionId: CampRegistrationsEditionId,
        @NotNull
        private var editionStartDate: LocalDate,

        @NotNull
        private var editionEndDate: LocalDate,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "value", column = Column(name = "totalPrice_value")),
                AttributeOverride(name = "currency", column = Column(name = "totalPrice_currency"))
        )
        private var totalPrice: Money,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "value", column = Column(name = "downPaymentAmount_value")),
                AttributeOverride(name = "currency", column = Column(name = "downPaymentAmount_currency"))
        )
        private var downPaymentAmount: Money?
) : AggregateRoot<CampRegistrationsEditionId, CampRegistrationsEditionEvent>(campRegistrationsEditionId), Versioned {

    @Version
    private var version: Long? = null

    @NotNull
    @OneToOne(cascade = [CascadeType.ALL])
    private var campRegistrations: CampRegistrations = CampRegistrations(campRegistrationsEditionId)


    init {
        registerEvent(
                CampRegistrationsCreated(
                        getAggregateId(),
                        campRegistrations.entityId,
                        campRegistrations.getTimerSettings()
                )
        )
    }

    fun updateCampEditionDuration(editionStartDate: LocalDate, editionEndDate: LocalDate) {
        this.editionStartDate = editionStartDate
        this.editionEndDate = editionEndDate
    }

    fun canUpdateCampRegistrationsTimer(timerSettings: TimerSettings, currentTime: ZonedDateTime) =
            campRegistrations.canUpdateTimerSettings(timerSettings, currentTime)


    fun updateCampRegistrationsTimer(timerSettings: TimerSettings, currentTime: ZonedDateTime) {
        canUpdateCampRegistrationsTimer(timerSettings, currentTime)
                .ifInvalidThrowException()

        campRegistrations.updateTimerSettings(timerSettings, currentTime)
    }

    fun canStartNowCampRegistrations(currentTime: ZonedDateTime, canStartSpec: CampRegistrationsCanStartSpecification): ValidationResult {
        val validationResultBuffer = ValidationResult.buffer()
        campRegistrations.canStartNow(currentTime).doIfInvalid { validationResultBuffer.addViolatedRules(it.violatedRules) }
        validationResultBuffer.addViolatedRuleIfNot(CampRegistrationsDomainRule.ONLY_ONE_CAMP_REGISTRATIONS_CAN_BE_IN_PROGRESS_IN_THE_SAME_TIME, canStartSpec.isSatisfiedBy(this))
        return validationResultBuffer.toValidationResult()
    }

    fun startNowCampRegistrations(currentTime: ZonedDateTime, canStartSpec: CampRegistrationsCanStartSpecification) {
        canStartNowCampRegistrations(currentTime, canStartSpec)
                .ifInvalidThrowException()

        campRegistrations.startNow(currentTime)

        registerEvent(
                CampRegistrationsStarted(
                        getAggregateId(),
                        campRegistrations.entityId,
                        campRegistrations.getTimerSettings()
                )
        )
    }

    fun canFinishNowCampRegistrations(currentTime: ZonedDateTime) = campRegistrations.canFinishNow(currentTime)

    fun finishNowCampRegistrations(currentTime: ZonedDateTime) {
        canFinishNowCampRegistrations(currentTime)

        registerEvent(
                CampRegistrationsFinished(
                        getAggregateId(),
                        campRegistrations.entityId
                )
        )
    }

    fun canSuspendNowCampRegistrations() =
            campRegistrations.canSuspend()

    fun suspendNowCampRegistrations(currentTime: ZonedDateTime) {
        campRegistrations.suspend(currentTime)

        registerEvent(
                CampRegistrationsSuspended(
                        getAggregateId(),
                        campRegistrations.entityId
                )
        )
    }

    fun canUnsuspendNowCampRegistrations() =
            campRegistrations.canUnsuspend()

    fun unsuspendNowCampRegistrations(currentTime: ZonedDateTime) {
        campRegistrations.unsuspend(currentTime)

        registerEvent(
                CampRegistrationsUnsuspended(
                        getAggregateId(),
                        campRegistrations.entityId
                )
        )
    }

    fun campRegistrationsInProgress() = campRegistrations.isInProgress()

    fun createAcademicMinistryCottage(academicMinistry: CampRegistrationsAcademicMinistry): Cottage {
        return Cottage(
                campRegistrationsEditionId = getAggregateId(),
                cottageType = CottageType.ACADEMIC_MINISTRY,
                academicMinistryId = academicMinistry.getAggregateId(),
                name = academicMinistry.getShortName() ?: academicMinistry.getOfficialName(),
                logoImageUrl = academicMinistry.getLogoImageUrl()
        )
    }

    fun createStandaloneCottage(cottageName: String) =
            Cottage(
                    campRegistrationsEditionId = getAggregateId(),
                    cottageType = CottageType.STANDALONE,
                    name = cottageName,
                    academicMinistryId = null
            )

    fun getCampEditionStartDate() = editionStartDate
    fun getCampEditionEndDate() = editionEndDate
    fun getTotalPrice() = totalPrice
    fun getDownPaymentAmount() = downPaymentAmount
    override fun getVersion() = version
    fun getCampRegistrationsStatus() = campRegistrations.getStatus()

    fun getSnapshot() = CampRegistrationsEditionSnapshot(
            campRegistrationsEditionId = getAggregateId(),
            editionStartDate = editionStartDate,
            editionEndDate = editionEndDate,
            editionPrice = totalPrice,
            editionDownPaymentAmount = downPaymentAmount,
            campRegistrations = campRegistrations.getSnapshot()
    )
}
