package org.bialydunajec.campschedule.domain.valueobject

import org.bialydunajec.campschedule.eventsourcing.AggregateIdentifier

class CampEditionScheduleId(campEditionId: Int) : AggregateIdentifier(campEditionId.toString())
