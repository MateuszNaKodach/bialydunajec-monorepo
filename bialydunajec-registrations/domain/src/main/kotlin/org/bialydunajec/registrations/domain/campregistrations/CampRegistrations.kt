package org.bialydunajec.registrations.domain.campregistrations

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.jetbrains.annotations.NotNull
import java.time.Instant
import javax.persistence.Entity

@Entity
class CampRegistrations(
        campRegistrationsId: CampRegistrationsId,
        @NotNull
        val startDate: Instant? = null,

        @NotNull
        val endDate: Instant? = null
) : AggregateRoot<CampRegistrationsId, CampRegistrationsEvent>(campRegistrationsId) {

    fun isInProgress() = isStarted() && !isEnded()

    fun isStarted() = Instant.now().isAfter(startDate)

    fun isEnded() = Instant.now().isAfter(endDate)

    fun createAcademicMinistryCottage(academicMinistry: AcademicMinistry): Cottage {
        return Cottage(
                getAggregateId(),
                academicMinistry.getAggregateId(),
                academicMinistry.shortName,
                academicMinistry.logoImageUrl
        )
    }

    fun createStandaloneCottage(){

    }
}