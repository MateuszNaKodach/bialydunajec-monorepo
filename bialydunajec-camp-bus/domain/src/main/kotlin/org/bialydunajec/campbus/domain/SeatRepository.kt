package org.bialydunajec.campbus.domain

internal interface SeatRepository {

    fun save(campBusSeat: Seat)
    fun findById()

}