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

    fun getCampEditionDuration() = LocalDateRange(start = startDate, end = endDate)

    fun updateCampEditionDuration(campEditionDuration: LocalDateRange) {
        startDate = campEditionDuration.start
        endDate = campEditionDuration.end

        val campRegistrationsStartDate = getCampRegistrationsStartDate()
        //if (campRegistrationsStartDate != null && campRegistrationsStartDate.isAfter(endDate)) {
        //    campRegistrations.endDate = endDate
        //}
    }

    fun updateCampRegistrationsDuration(registrationDuration: ZonedDateTimeRange) {
        campRegistrations.startDate = registrationDuration.start
        campRegistrations.endDate = registrationDuration.end
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
}
