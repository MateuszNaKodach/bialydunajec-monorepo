package org.bialydunajec.application.campbus

import org.bialydunajec.application.eventsourcing.DomainCommandMessage
import org.bialydunajec.application.eventsourcing.DomainEventMessage
import org.bialydunajec.campbus.domain.*

internal class ReserveSeatApplicationService(
        private val seatRepository: SeatRepository
) {

    fun execute(domainCommandMessage: DomainCommandMessage<SeatCommand.ReserveSeat>): DomainEventMessage<SeatEvent> {
        val command = domainCommandMessage.domainCommand
        val aggreagate = loadAggregateById(command.aggregateId)
        val result = execute(aggreagate, command)
        return DomainEventMessage.withEvent(result)
                .causedBy(domainCommandMessage)
                .build()
    }


    fun loadAggregateById(aggregateId: SeatId) =
            seatRepository.findById(aggregateId)!! //TODO: Publish technical failure if null!!!


    fun execute(aggregate: Seat, command: SeatCommand.ReserveSeat): SeatEvent {
        val seat = aggregate.handle(command)
        val lastEvent = seat.changes.last()
        seatRepository.save(seat) //How to pass causationId here?
        return lastEvent
    }

}
