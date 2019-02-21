package org.bialydunajec.campschedule.domain

import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.bialydunajec.campschedule.domain.valueobject.DayActivityDetails
import org.bialydunajec.campschedule.domain.valueobject.DayActivityId
import java.time.LocalDate


sealed class CampEditionScheduleEvent {
    data class CampEditionSchedulingStarted(
            val campEditionScheduleId: CampEditionScheduleId,
            val campEditionStartDate: LocalDate,
            val campEditionEndDate: LocalDate
    )

    data class CampDayScheduled(
            val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val date: LocalDate
    )

    data class CampDayActivityScheduled(
            val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val dayActivityId: DayActivityId = DayActivityId(),
            val dayActivityDetails: DayActivityDetails
    )

    data class CampDayActivityRescheduled(
            val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val dayActivityId: DayActivityId = DayActivityId(),
            val dayActivityDetails: DayActivityDetails
    )

    data class CampDayActivityCancelled(
            val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val dayActivityId: DayActivityId = DayActivityId(),
            val dayActivityDetails: DayActivityDetails
    )

    data class CampDayCancelled(
            val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val date: LocalDate
    )

}
