package org.bialydunajec.campbus.domain

interface SeatRepository {

    fun save(seat: Seat)
    fun findById(seatId: SeatId): Seat?

}