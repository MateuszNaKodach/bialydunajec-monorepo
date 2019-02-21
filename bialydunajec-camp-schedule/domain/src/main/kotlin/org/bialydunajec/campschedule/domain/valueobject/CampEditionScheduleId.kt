package org.bialydunajec.campschedule.domain.valueobject

import org.bialydunajec.campschedule.eventsourcing.AggregateIdentifier

class CampEditionScheduleId(campEditionId: String) : AggregateIdentifier(campEditionId) {
    constructor(campEditionId: Int) : this(campEditionId.toString())
}
