package org.bialydunajec.application.campbus

import org.bialydunajec.campbus.domain.*

internal class ReserveSeatApplicationService(
        private val seatRepository: SeatRepository
) {

    fun execute(command: SeatCommand.ReserveSeat) {
        val aggreagate = loadAggregateById(command.aggregateId)
        execute(aggreagate, command)
    }


    fun loadAggregateById(aggregateId: SeatId) =
            seatRepository.findById(aggregateId)!! //TODO: Publish technical failure if null!!!


    fun execute(aggregate: Seat, command: SeatCommand.ReserveSeat): SeatEvent {
        val seat = aggregate.handle(command)
        val lastEvent = seat.changes.last()
        seatRepository.save(seat)
        return lastEvent
    }

}