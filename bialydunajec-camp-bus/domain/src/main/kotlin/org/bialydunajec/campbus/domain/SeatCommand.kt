package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.DomainCommand

sealed class SeatCommand(
        aggregateId: SeatId) : DomainCommand<SeatId>(aggregateId) {

    class AddSeatForCourse(aggregateId: SeatId, val campBusCourseId: BusCourseId)
        : SeatCommand(aggregateId)

    class ReserveSeat(aggregateId: SeatId, val passengerId: PassengerId)
        : SeatCommand(aggregateId)

    class CancelReservation(aggregateId: SeatId)
        : SeatCommand(aggregateId)

    class ConfirmReservation(aggregateId: SeatId)
        : SeatCommand(aggregateId)

    class RemoveSeatFromCourse(aggregateId: SeatId)
        : SeatCommand(aggregateId)

    class ReleaseSeat(aggregateId: SeatId)
        : SeatCommand(aggregateId)

    override fun toString() =
            "${this.javaClass.simpleName}(aggregateId=$aggregateId)"

}
