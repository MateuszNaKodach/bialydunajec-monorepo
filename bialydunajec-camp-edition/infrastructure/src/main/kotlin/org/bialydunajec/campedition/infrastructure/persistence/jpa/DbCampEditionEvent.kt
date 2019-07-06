package org.bialydunajec.campedition.infrastructure.persistence.jpa

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import java.time.LocalDate

sealed class DbCampEditionEvent(
        override val aggregateId: DbCampEditionId
) : DomainEvent<DbCampEditionId> {

    class CampEditionCreated(
            campEditionId: DbCampEditionId,
            val startDate: LocalDate,
            val endDate: LocalDate,
            val totalPrice: Money,
            val downPaymentAmount: Money?
    ) : DbCampEditionEvent(campEditionId)

    class CampEditionDurationUpdated(
            campEditionId: DbCampEditionId,
            val startDate: LocalDate,
            val endDate: LocalDate
    ) : DbCampEditionEvent(campEditionId)
}
