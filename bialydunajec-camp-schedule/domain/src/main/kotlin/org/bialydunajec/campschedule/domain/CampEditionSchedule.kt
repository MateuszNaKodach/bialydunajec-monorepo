package org.bialydunajec.campschedule.domain

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.modelling.command.AggregateMember
import org.axonframework.modelling.command.ForwardMatchingInstances
import org.axonframework.spring.stereotype.Aggregate
import org.bialydunajec.campschedule.domain.entity.CampDay
import org.bialydunajec.campschedule.domain.exception.CampEditionScheduleDomainRule
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.bialydunajec.ddd.domain.base.validation.CheckDomainRule
import org.bialydunajec.ddd.domain.base.validation.ValidationResult
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import java.lang.IllegalStateException

@Aggregate
internal class CampEditionSchedule() {

    @AggregateIdentifier
    private lateinit var campEditionScheduleId: CampEditionScheduleId

    @AggregateMember(eventForwardingMode = ForwardMatchingInstances::class)
    private var days: MutableList<CampDay> = mutableListOf()

    @CommandHandler
    constructor(command: CampEditionScheduleCommand.StartCampEditionScheduling) : this() {
        apply(CampEditionScheduleEvent.CampEditionSchedulingStarted(command.campEditionScheduleId))
    }

    @EventSourcingHandler
    private fun on(event: CampEditionScheduleEvent.CampEditionSchedulingStarted) {
        campEditionScheduleId = event.campEditionScheduleId
    }

    @CommandHandler
    fun handle(command: CampEditionScheduleCommand.ScheduleNewCampDay) {
        command.run {
            CheckDomainRule(
                    CampEditionScheduleDomainRule.CAMP_DAY_DATE_CAN_NOT_BE_DUPLICATED_IN_ONE_SCHEDULE,
                    days.none { it.date == date }
            ).ifViolatedThrowException()

            apply(CampEditionScheduleEvent.NewCampDayScheduled(campEditionScheduleId, campDayId, date))
        }
    }

    @EventSourcingHandler
    private fun on(event: CampEditionScheduleEvent.NewCampDayScheduled) {
        event.run {
            days.add(CampDay(campDayId, date))
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
