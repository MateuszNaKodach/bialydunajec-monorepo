package org.bialydunajec.eventsourcing.domain

import org.bialydunajec.campbus.domain.SeatId

interface DomainCommand<AggregateIdType : AggregateId> {
    val aggregateId: SeatId
    val aggregateVersion: AggregateVersion
}
