package org.bialydunajec.registrations.domain.campregistrations

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageType
import org.jetbrains.annotations.NotNull
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = "camp-registrations")
class CampRegistrations(
        campRegistrationsId: CampRegistrationsId,
        @NotNull
        private val startDate: Instant? = null,

        @NotNull
        private val endDate: Instant? = null
) : AggregateRoot<CampRegistrationsId, CampRegistrationsEvent>(campRegistrationsId) {

    fun getStartDate() = startDate

    fun getEndDate() = endDate

    fun isInProgress() = isStarted() && !isEnded()

    fun isStarted() = Instant.now().isAfter(startDate)

    fun isEnded() = Instant.now().isAfter(endDate)

    fun createAcademicMinistryCottage(academicMinistry: AcademicMinistry): Cottage {
        return Cottage(
                campRegistrationsId = getAggregateId(),
                cottageType = CottageType.ACADEMIC_MINISTRY,
                academicMinistryId = academicMinistry.getAggregateId(),
                name = academicMinistry.getShortName(),
                logoImageUrl = academicMinistry.getLogoImageUrl()
        )
    }

    fun createStandaloneCottage(cottageName: String) =
            Cottage(
                    campRegistrationsId = getAggregateId(),
                    cottageType = CottageType.STANDALONE,
                    name = cottageName,
                    academicMinistryId = null
            )

}