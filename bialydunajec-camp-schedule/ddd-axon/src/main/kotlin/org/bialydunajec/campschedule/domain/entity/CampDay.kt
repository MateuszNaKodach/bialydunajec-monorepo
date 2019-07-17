package org.bialydunajec.campschedule.domain.entity

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.EntityId
import org.bialydunajec.campschedule.domain.CampEditionScheduleCommand
import org.bialydunajec.campschedule.domain.CampEditionScheduleEvent
import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import org.bialydunajec.campschedule.domain.valueobject.CampDayStatus
import java.time.LocalDate
import org.axonframework.modelling.command.AggregateLifecycle.apply as applyEvent

class CampDay(
        @EntityId
        val campDayId: CampDayId,
        var date: LocalDate,
        var dayActivities: MutableList<DayActivity> = mutableListOf(),
        var status: CampDayStatus = CampDayStatus.SCHEDULED
) {

    fun isCancelled() = status == CampDayStatus.CANCELLED

    fun cancel() {
        status = CampDayStatus.CANCELLED
    }

    @CommandHandler
    fun handle(command: CampEditionScheduleCommand.ScheduleCampDayActivity) {
        command.run {
            applyEvent(CampEditionScheduleEvent.CampDayActivityScheduled(campEditionScheduleId, campDayId, dayActivityId, dayActivityDetails))
        }
    }

    @EventSourcingHandler
    private fun on(event: CampEditionScheduleEvent.CampDayActivityScheduled) {
        if (event.campDayId == campDayId) {
            event.run {
                dayActivities.add(DayActivity(dayActivityId, dayActivityDetails))
            }
        }
    }

    @CommandHandler
    fun handle(command: CampEditionScheduleCommand.RescheduleCampDayActivity) {
        command.run {
            applyEvent(CampEditionScheduleEvent.CampDayActivityRescheduled(campEditionScheduleId, campDayId, dayActivityId, dayActivityDetails))
        }
    }

    @EventSourcingHandler
    private fun on(event: CampEditionScheduleEvent.CampDayActivityRescheduled) {
        if (event.campDayId == campDayId) {
            event.run {
                dayActivities.find { it.dayActivityId == event.dayActivityId }
                        ?.let { it.details = event.dayActivityDetails }
            }
        }
    }

    @CommandHandler
    fun handle(command: CampEditionScheduleCommand.CancelCampDayActivity) {
        command.run {
            dayActivities.find { it.dayActivityId == command.dayActivityId }
                    ?.let {
                        applyEvent(CampEditionScheduleEvent.CampDayActivityCancelled(campEditionScheduleId, campDayId, dayActivityId, it.details))
                    }
        }
    }

    @EventSourcingHandler
    private fun on(event: CampEditionScheduleEvent.CampDayActivityCancelled) {
        dayActivities.removeIf { it.dayActivityId == event.dayActivityId }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CampDay

        if (campDayId != other.campDayId) return false

        return true
    }

    override fun hashCode(): Int {
        return campDayId.hashCode()
    }
}
