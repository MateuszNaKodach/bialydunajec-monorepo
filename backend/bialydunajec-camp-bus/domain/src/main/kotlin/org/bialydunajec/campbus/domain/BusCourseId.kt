package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.AggregateId
import java.util.*

class BusCourseId(id: String = UUID.randomUUID().toString()) : AggregateId(id)