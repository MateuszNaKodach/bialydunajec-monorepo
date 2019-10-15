package org.bialydunajec.campbus

import org.bialydunajec.campbus.domain.Seat
import org.bialydunajec.campbus.domain.SeatId
import org.bialydunajec.campbus.domain.SeatRepository

internal class EventSourcedSeatRepository : SeatRepository {

    override fun save(seat: Seat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(seatId: SeatId): Seat? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}