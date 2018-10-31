package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import java.time.LocalDate

sealed class CampEditionEvent(
        override val aggregateId: CampEditionId
) : DomainEvent<CampEditionId> {

    class CampEditionCreated(
            campEditionId: CampEditionId,
            val startDate: LocalDate,
            val endDate: LocalDate,
            val price: Money
    ) : CampEditionEvent(campEditionId)

    class CampEditionDurationUpdated(
            campEditionId: CampEditionId,
            val startDate: LocalDate,
            val endDate: LocalDate
    ) : CampEditionEvent(campEditionId)
}