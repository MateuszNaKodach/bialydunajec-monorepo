package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.LocalDateRange
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.ZonedDateTimeRange
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrations
import org.bialydunajec.registrations.domain.campedition.specification.CampRegistrationsHasMinimumCottagesToStartSpecification
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageType
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.jetbrains.annotations.NotNull
import java.time.Instant
import java.time.LocalDate
import java.time.ZonedDateTime
import org.bialydunajec.registrations.domain.campedition.CampEditionEvent.*
import javax.persistence.*

//TODO: CampEdition musi miec jako entity CampRegistrations i dbac np. o daty, zeby rejestracja nie trwała dłużej niz koniec obozu!
/**
 * Camp Edition in Camp Registrations Bounded Context
 */
@Entity
@Table(schema = "camp_registrations")
class CampEdition constructor(
        campEditionId: CampEditionId,
        @NotNull
        private var startDate: LocalDate,

        @NotNull
        private var endDate: LocalDate
) : AggregateRoot<CampEditionId, CampEditionEvent>(campEditionId), Versioned {

    @Version
    private var version: Long? = null

    @NotNull
    @OneToOne(cascade = [CascadeType.ALL])
    private var campRegistrations: CampRegistrations = CampRegistrations(campEditionId)

    init {
        registerEvent(
                CampRegistrationsCreated(
                        getAggregateId(),
                        campRegistrations.entityId,
                        campRegistrations.getTimerSettings()
                )
        )
    }

    fun updateCampRegistrationsTimer(timerSettings: TimerSettings, currentTime: ZonedDateTime) {
        campRegistrations.updateTimerSettings(timerSettings, currentTime)
    }

    fun canStartNowCampRegistrations(currentTime: ZonedDateTime, minCottagesSpec: CampRegistrationsHasMinimumCottagesToStartSpecification): ValidationResult {
        val validationResultBuffer = ValidationResult.buffer()
        campRegistrations.canStartNow(currentTime).doIfInvalid { validationResultBuffer.addViolatedRules(it.violatedRules) }
        validationResultBuffer.addViolatedRuleIfNot(CampRegistrationsDomainRule.CAMP_REGISTERS_HAS_TO_HAVE_MIN_COTTAGES_TO_START, minCottagesSpec.isSatisfiedBy(this))
        return validationResultBuffer.toValidationResult()
    }

    fun startNowCampRegistrations(currentTime: ZonedDateTime, minCottagesSpec: CampRegistrationsHasMinimumCottagesToStartSpecification) {
        canStartNowCampRegistrations(currentTime, minCottagesSpec)
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

    fun finishNowCampRegistrations(currentTime: ZonedDateTime) {
        campRegistrations.finishNow(currentTime)

        registerEvent(
                CampRegistrationsFinished(
                        getAggregateId(),
                        campRegistrations.entityId
                )
        )
    }

    fun suspendNowCampRegistrations(currentTime: ZonedDateTime) {
        campRegistrations.suspend(currentTime)

        registerEvent(
                CampRegistrationsSuspended(
                        getAggregateId(),
                        campRegistrations.entityId
                )
        )
    }

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

    fun createAcademicMinistryCottage(academicMinistry: AcademicMinistry): Cottage {
        return Cottage(
                campEditionId = getAggregateId(),
                cottageType = CottageType.ACADEMIC_MINISTRY,
                academicMinistryId = academicMinistry.getAggregateId(),
                name = academicMinistry.getShortName(),
                logoImageUrl = academicMinistry.getLogoImageUrl()
        )
    }

    fun createStandaloneCottage(cottageName: String) =
            Cottage(
                    campEditionId = getAggregateId(),
                    cottageType = CottageType.STANDALONE,
                    name = cottageName,
                    academicMinistryId = null
            )

    fun getCampEditionStartDate() = startDate
    fun getCampEditionEndDate() = endDate
    override fun getVersion() = version
}
