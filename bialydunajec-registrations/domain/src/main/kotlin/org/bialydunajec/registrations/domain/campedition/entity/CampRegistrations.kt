package org.bialydunajec.registrations.domain.campedition.entity

import org.bialydunajec.ddd.domain.base.exception.BusinessRuleViolationException
import org.bialydunajec.ddd.domain.base.persistence.IdentifiedEntity
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.ZonedDateTimeRange
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.RegistrationsStatus
import org.bialydunajec.registrations.domain.exception.CampersRegisterDomainErrorCode.*
import org.jetbrains.annotations.NotNull
import java.time.Instant
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

    internal fun canUpdateDuration() = status != RegistrationsStatus.IN_PROGRESS && status!= RegistrationsStatus.FINISHED

    internal fun updateDuration(startDate: ZonedDateTime?, endDate: ZonedDateTime?) {
        if(canUpdateDuration()){
            throw BusinessRuleViolationException.of(IN_PROGRESS_CAMP_REGISTRATIONS_CANNOT_BE_UPDATED)
        }
        val isUpdate = this.startDate != startDate && this.endDate != endDate
        if (isUpdate) {
            this.startDate = startDate
            this.endDate = endDate
            //TODO: Update event
            if (nonNull(startDate) && nonNull(endDate)) {
                status = RegistrationsStatus.CONFIGURED
            } else {
                status = RegistrationsStatus.UNCONFIGURED
            }
            //TODO: Change status event!
        }
    }

    internal fun canActivate() = status == RegistrationsStatus.CONFIGURED

    internal fun activate() {
        if (canActivate()) {
            throw BusinessRuleViolationException(NOT_CONFIGURED_CAMP_REGISTRATIONS_CANNOT_BE_ACTIVATED)
        }
        status = RegistrationsStatus.ACTIVATED
        //TODO: Add event
    }

    internal fun startNow(){

    }

    internal fun startIfStartDateIsAfter(currentTime: ZonedDateTime){

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