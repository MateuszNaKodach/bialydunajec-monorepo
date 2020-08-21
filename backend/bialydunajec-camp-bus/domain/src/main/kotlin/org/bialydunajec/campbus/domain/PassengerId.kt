package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.AggregateId
import java.util.*

class PassengerId(id: String = UUID.randomUUID().toString()) : AggregateId(id)