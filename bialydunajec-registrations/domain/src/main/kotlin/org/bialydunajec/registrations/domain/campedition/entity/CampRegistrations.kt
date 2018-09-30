package org.bialydunajec.registrations.domain.campedition.entity

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.RegistrationsStatus
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule.*
import org.jetbrains.annotations.NotNull
import java.time.ZonedDateTime
import java.util.Objects.nonNull
import javax.persistence.*

@Entity
//@Table(schema = "camp_registrations")
internal class CampRegistrations constructor(
        campEditionId: CampEditionId,

        @NotNull
        private var startDate: ZonedDateTime? = null,

        @NotNull
        private var endDate: ZonedDateTime? = null
) : IdentifiedEntity<CampRegistrationsId> {

    private var status: RegistrationsStatus = RegistrationsStatus.UNCONFIGURED

    @EmbeddedId
    override val entityId: CampRegistrationsId = CampRegistrationsId(campEditionId)

    internal fun canUpdateDuration() =
            ValidationResult.buffer()
                    .addViolatedRule(
                            IN_PROGRESS_CAMP_REGISTRATIONS_CANNOT_BE_UPDATED,
                            status == RegistrationsStatus.IN_PROGRESS
                    )
                    .addViolatedRule(
                            FINISHED_CAMP_REGISTRATIONS_CANNOT_BE_UPDATED,
                            status == RegistrationsStatus.FINISHED
                    )
                    .toValidationResult()

    internal fun updateDuration(startDate: ZonedDateTime?, endDate: ZonedDateTime?) {
        canUpdateDuration()
                .ifInvalidThrowException()

        val isUpdate = this.startDate != startDate && this.endDate != endDate
        if (isUpdate) {
            this.startDate = startDate
            this.endDate = endDate
            //TODO: Update event - consider how to do it with events, maybe return them from methods!?
            if (nonNull(startDate) && nonNull(endDate)) {
                status = RegistrationsStatus.CONFIGURED
            } else {
                status = RegistrationsStatus.UNCONFIGURED
            }
            //TODO: Change status event!
        }
    }

    internal fun canStart() = status == RegistrationsStatus.CONFIGURED

    internal fun startIfStartDateIsAfter(currentTime: ZonedDateTime) {

    }

    internal fun startNow(currentTime: ZonedDateTime) {
        if (canStart()) {
            throw DomainRuleViolationException(NOT_CONFIGURED_CAMP_REGISTRATIONS_CANNOT_BE_ACTIVATED)
        }
        status = RegistrationsStatus.IN_PROGRESS
        //TODO: Add event
    }


/*internal fun isStarted(currentTime: ZonedDateTime) = currentTime.isAfter(startDate)

internal fun isEnded(currentTime: ZonedDateTime) = currentTime.isAfter(endDate)
*/

    internal fun removeStartDate() {

    }

    internal fun removeEndDate() {

    }

//internal fun canStart() = nonNull(endDate)

/*internal fun start() {
    this.status = RegistrationsStatus.ACTIVATED
}*/
}