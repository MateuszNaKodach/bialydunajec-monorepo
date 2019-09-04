package org.bialydunajec.eventsourcing.domain

import org.bialydunajec.campbus.domain.SeatId

interface Command<AggregateIdType : AggregateId> {
    val aggregateId: SeatId
    val aggregateVersion: AggregateVersion
}