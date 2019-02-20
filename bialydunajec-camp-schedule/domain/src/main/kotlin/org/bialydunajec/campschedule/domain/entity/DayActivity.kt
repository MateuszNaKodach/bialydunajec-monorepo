package org.bialydunajec.campschedule.domain.entity

import org.axonframework.modelling.command.EntityId
import org.bialydunajec.campschedule.domain.valueobject.DayActivityId
import java.time.Instant

//TODO: Czy dotyczy całego obozu albo danych chatek. W 1 wersji dla całego obozu!
class DayActivity(
        @EntityId
        val dayActivityId: DayActivityId,
        var startDate: Instant,
        var endDate: Instant,
        var title: String,
        var description: String,
        var photoUrl: String
) {


}
