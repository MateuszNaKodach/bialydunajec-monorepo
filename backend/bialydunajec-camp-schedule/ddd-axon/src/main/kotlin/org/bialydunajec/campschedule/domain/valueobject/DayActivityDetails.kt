package org.bialydunajec.campschedule.domain.valueobject

import org.bialydunajec.campschedule.domain.exception.CampEditionScheduleDomainRule.*
import org.bialydunajec.ddd.domain.base.validation.DomainRuleChecker
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import java.time.LocalTime

data class DayActivityDetails(
        val title: String,
        val startTime: LocalTime,
        val endTime: LocalTime? = null,
        val description: String? = null,
        val photoUrl: Url? = null
) {
    init {
        DomainRuleChecker
                .check(DAY_ACTIVITY_START_TIME_CAN_NOT_BE_AFTER_END_TIME) { endTime?.let { startTime.isAfter(it).not() } }
                ?.check(DAY_ACTIVITY_END_TIME_CAN_NOT_BE_BEFORE_START_TIME) { endTime?.isBefore(startTime)?.not() }
    }
}
