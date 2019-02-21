package org.bialydunajec.campschedule.domain

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply as applyEvent
import org.axonframework.modelling.command.AggregateMember
import org.axonframework.modelling.command.ForwardMatchingInstances
import org.axonframework.spring.stereotype.Aggregate
import org.bialydunajec.campschedule.domain.entity.CampDay
import org.bialydunajec.campschedule.domain.exception.CampEditionScheduleDomainRule.*
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.bialydunajec.ddd.domain.base.validation.DomainRuleChecker
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import java.time.LocalDate

@Aggregate
internal class CampEditionSchedule() {

    @AggregateIdentifier
    private lateinit var campEditionScheduleId: CampEditionScheduleId

    private lateinit var campEditionStartDate: LocalDate

    private lateinit var campEditionEndDate: LocalDate

    @AggregateMember(eventForwardingMode = ForwardMatchingInstances::class)
    private var days: MutableList<CampDay> = mutableListOf()

    @CommandHandler
    constructor(command: CampEditionScheduleCommand.StartCampEditionScheduling) : this() {
        command.run {
            applyEvent(CampEditionScheduleEvent.CampEditionSchedulingStarted(campEditionScheduleId, campEditionStartDate, campEditionEndDate))
        }
    }

    @EventSourcingHandler
    private fun on(event: CampEditionScheduleEvent.CampEditionSchedulingStarted) {
        campEditionScheduleId = event.campEditionScheduleId
        campEditionStartDate = event.campEditionStartDate
        campEditionEndDate = event.campEditionEndDate
    }

    @CommandHandler
    fun handle(command: CampEditionScheduleCommand.ScheduleCampDay) {
        command.run {
            DomainRuleChecker
                    .check(CAMP_DAY_DATE_CAN_NOT_BE_DUPLICATED_IN_ONE_SCHEDULE) { days.filter { it.isCancelled().not() }.none { it.date == date } }
                    ?.check(CAMP_DAY_CAN_NOT_BE_SCHEDULE_BEFORE_CAMP_EDITION_START) { !date.isBefore(campEditionStartDate) }
                    ?.check(CAMP_DAY_CAN_NOT_BE_SCHEDULE_AFTER_CAMP_EDITION_END) { !date.isAfter(campEditionEndDate) }

            applyEvent(CampEditionScheduleEvent.CampDayScheduled(campEditionScheduleId, campDayId, date))
        }
    }

    @EventSourcingHandler
    private fun on(event: CampEditionScheduleEvent.CampDayScheduled) {
        event.run {
            days.find { event.campDayId == it.campDayId && it.isCancelled() }
                    ?.let {
                        days.add(CampDay(event.campDayId, it.date))
                        days.remove(it)
                    } ?: days.add(CampDay(campDayId, date))
        }
    }

    @CommandHandler
    fun handle(command: CampEditionScheduleCommand.CancelCampDay) {
        command.run {
            days.find { it.campDayId == command.campDayId && it.isCancelled().not() }
                    ?.let {
                        applyEvent(CampEditionScheduleEvent.CampDayCancelled(campEditionScheduleId, campDayId, it.date))
                    } ?: throw DomainRuleViolationException.of(DAY_TO_CANCEL_MUST_EXISTS)
        }
    }

    @EventSourcingHandler
    private fun on(event: CampEditionScheduleEvent.CampDayCancelled) {
        event.run {
            days.find { it.campDayId == event.campDayId }?.cancel()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CampEditionSchedule

        if (campEditionScheduleId != other.campEditionScheduleId) return false

        return true
    }

    override fun hashCode(): Int {
        return campEditionScheduleId.hashCode()
    }


}

//@TargetAggregateVersion
