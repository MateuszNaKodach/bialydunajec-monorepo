package org.bialydunajec.campschedule.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.bialydunajec.campschedule.domain.valueobject.DayActivityDetails
import org.bialydunajec.campschedule.domain.valueobject.DayActivityId
import java.time.LocalDate


sealed class CampEditionScheduleCommand {
    data class StartCampEditionScheduling(
            @TargetAggregateIdentifier val campEditionScheduleId: CampEditionScheduleId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    )

    data class ScheduleCampDay(
            @TargetAggregateIdentifier val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId = CampDayId(),
            val date: LocalDate
    )

    data class ScheduleCampDayActivity(
            @TargetAggregateIdentifier val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val dayActivityId: DayActivityId = DayActivityId(),
            val dayActivityDetails: DayActivityDetails
    )

    data class RescheduleCampDayActivity(
            @TargetAggregateIdentifier val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val dayActivityId: DayActivityId,
            val dayActivityDetails: DayActivityDetails
    )

    data class CancelCampDayActivity(
            @TargetAggregateIdentifier val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val dayActivityId: DayActivityId
    )

    data class CancelCampDay(
            @TargetAggregateIdentifier val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId
    )
}
