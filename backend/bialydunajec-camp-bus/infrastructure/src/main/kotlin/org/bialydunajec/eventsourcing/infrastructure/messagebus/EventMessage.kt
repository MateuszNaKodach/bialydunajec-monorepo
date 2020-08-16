package org.bialydunajec.eventsourcing.infrastructure.messagebus

import java.time.Instant

internal interface EventMessage<PayloadType> : Message<PayloadType> {

    val timestamp: Instant
}
