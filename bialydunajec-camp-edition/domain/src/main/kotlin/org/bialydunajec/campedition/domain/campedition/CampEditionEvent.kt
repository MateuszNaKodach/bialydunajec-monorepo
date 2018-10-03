package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

sealed class CampEditionEvent(
        override val aggregateId: CampEditionId
) : DomainEvent<CampEditionId> {

    class CampEditionCreated(
            campEditionId: CampEditionId,
            val startDate: LocalDate,
            val endDate: LocalDate
    ) : CampEditionEvent(campEditionId)

    class CampEditionDurationUpdated(
            campEditionId: CampEditionId,
            val startDate: LocalDate,
            val endDate: LocalDate
    ) : CampEditionEvent(campEditionId)
}