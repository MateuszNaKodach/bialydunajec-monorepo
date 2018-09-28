package org.bialydunajec.registrations.domain.campregistrations

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campregistrations.specification.InProgressCampRegistrationsSpecification
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageType
import org.jetbrains.annotations.NotNull
import java.time.Instant
import javax.persistence.*

//TODO: Consider merge camp edition with camp registrations - registrations setup as value object
@Entity
//@Table(schema = "camp_registrations")
class CampRegistrations internal constructor(
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campEditionId")))
        private val campEditionId: CampEditionId,

        @NotNull
        private val startDate: Instant? = null,

        @NotNull
        private val endDate: Instant? = null
) : AggregateRoot<CampRegistrationsId, CampRegistrationsEvent>(CampRegistrationsId(campEditionId)) {

    fun getCampEditionId() = campEditionId

    fun getStartDate() = startDate

    fun getEndDate() = endDate

    fun isInProgress() = InProgressCampRegistrationsSpecification().isSatisfiedBy(this)

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