package org.bialydunajec.campschedule.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import java.time.LocalDate


sealed class CampEditionScheduleCommand {
    data class StartCampEditionScheduling(
            @TargetAggregateIdentifier val campEditionScheduleId: CampEditionScheduleId
    )

    data class ScheduleNewCampDay(
            @TargetAggregateIdentifier val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId = CampDayId(),
            val date: LocalDate
    )
}
