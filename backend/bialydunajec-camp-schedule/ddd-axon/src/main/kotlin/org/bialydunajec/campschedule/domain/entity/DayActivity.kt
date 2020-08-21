package org.bialydunajec.campschedule.domain.entity

import org.axonframework.modelling.command.EntityId
import org.bialydunajec.campschedule.domain.valueobject.CampDayStatus
import org.bialydunajec.campschedule.domain.valueobject.DayActivityDetails
import org.bialydunajec.campschedule.domain.valueobject.DayActivityId

//TODO: Czy dotyczy całego obozu albo danych chatek. W 1 wersji dla całego obozu!
class DayActivity(
        @EntityId
        val dayActivityId: DayActivityId,
        var details: DayActivityDetails
) {
}
