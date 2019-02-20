package org.bialydunajec.campschedule.domain

import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import java.time.LocalDate


sealed class CampEditionScheduleEvent{
    data class CampEditionSchedulingStarted(
            val campEditionScheduleId: CampEditionScheduleId
    )

    data class NewCampDayScheduled(
            val campEditionScheduleId: CampEditionScheduleId,
            val campDayId: CampDayId,
            val date: LocalDate
    )
}
