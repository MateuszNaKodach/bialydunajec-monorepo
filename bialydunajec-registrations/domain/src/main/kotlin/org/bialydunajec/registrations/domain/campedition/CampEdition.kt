package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.LocalDateRange
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.ZonedDateTimeRange
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrations
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageType
import org.jetbrains.annotations.NotNull
import java.time.Instant
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToOne

//TODO: CampEdition musi miec jako entity CampRegistrations i dbac np. o daty, zeby rejestracja nie trwała dłużej niz koniec obozu!
/**
 * Camp Edition in Camp Registrations Bounded Context
 */
@Entity
//@Table(schema = "camp_registrations")
class CampEdition internal constructor(
        campEditionId: CampEditionId,
        @NotNull
        private var startDate: LocalDate? = null,

        @NotNull
        private var endDate: LocalDate? = null
) : AggregateRoot<CampEditionId, CampEditionEvent>(campEditionId) {

    @NotNull
    @OneToOne(cascade = [CascadeType.ALL])
    private var campRegistrations: CampRegistrations = CampRegistrations(campEditionId)



    fun updateCampRegistrationsDuration(registrationDuration: ZonedDateTimeRange) {
        campRegistrations.updateStartDate(registrationDuration.start)
        campRegistrations.updateEndDate(registrationDuration.end)
    }

    fun startCampRegistrations(){

    }

    fun canStartCampRegistrations(){

    }


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


    fun hasCampRegistrationsStarted(currentTime: ZonedDateTime) = campRegistrations.isStarted(currentTime)
    fun hasCampRegistrationsEnded(currentTime: ZonedDateTime) = campRegistrations.isEnded(currentTime)
    fun getCampRegistrationsStartDate() = campRegistrations.startDate
    fun getCampRegistrationsEndDate() = campRegistrations.endDate
    fun getCampEditionStartDate() = startDate
    fun getCampEditionEndDate() = endDate
}
