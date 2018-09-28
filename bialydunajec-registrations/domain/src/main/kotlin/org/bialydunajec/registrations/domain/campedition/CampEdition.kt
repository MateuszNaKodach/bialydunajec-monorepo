package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.InstantRange
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrations
import org.jetbrains.annotations.NotNull
import java.time.Instant
import javax.persistence.Entity

//TODO: CampEdition musi miec jako entity CampRegistrations i dbac np. o daty, zeby rejestracja nie trwała dłużej niz koniec obozu!
/**
 * Camp Edition in Camp Registrations Bounded Context
 */
@Entity
//@Table(schema = "camp_registrations")
class CampEdition(
        campEditionId: CampEditionId,
        @NotNull
        private val startDate: Instant? = null,

        @NotNull
        private val endDate: Instant? = null
) : AggregateRoot<CampEditionId, CampEditionEvent>(campEditionId) {
    fun getCampEditionDuration() = InstantRange(start = startDate, end = endDate)

    fun createCampRegistrations(registrationDuration: InstantRange) =
            CampRegistrations(
                    campEditionId = getAggregateId(),
                    startDate = registrationDuration.start,
                    endDate = registrationDuration.end
            )
}
