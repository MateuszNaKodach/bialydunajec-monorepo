package org.bialydunajec.eventsourcing.domain

import java.util.*

data class DomainEventId(val id: String = UUID.randomUUID().toString())