package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.Command

sealed class SeatCommand(override val aggregateId: SeatId, override val aggregateVersion: AggregateVersion) : Command<SeatId> {
    class AddSeatForCourse(aggregateId: SeatId, val campBusCourseId: BusCourseId) : SeatCommand(aggregateId, AggregateVersion.ZERO)
    class ReserveSeat(aggregateId: SeatId, aggregateVersion: AggregateVersion, val passengerId: PassengerId) : SeatCommand(aggregateId, aggregateVersion)
    class CancelReservation(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class ConfirmReservation(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class RemoveSeatFromCourse(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)
    class ReleaseSeat(aggregateId: SeatId, aggregateVersion: AggregateVersion) : SeatCommand(aggregateId, aggregateVersion)

    override fun toString() =
            "${this.javaClass.simpleName}(aggregateId=$aggregateId, aggregateVersion=$aggregateVersion)"

}