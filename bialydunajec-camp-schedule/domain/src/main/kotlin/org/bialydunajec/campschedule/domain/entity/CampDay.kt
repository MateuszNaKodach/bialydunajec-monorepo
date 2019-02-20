package org.bialydunajec.campschedule.domain.entity

import org.axonframework.modelling.command.EntityId
import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CampDay(
        @EntityId
        val campDayTimetableId: CampDayId,
        var date: LocalDate,
        var dayActivities: MutableList<DayActivity> = mutableListOf()
) {

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as CampDay

                if (campDayTimetableId != other.campDayTimetableId) return false

                return true
        }

        override fun hashCode(): Int {
                return campDayTimetableId.hashCode()
        }
}
