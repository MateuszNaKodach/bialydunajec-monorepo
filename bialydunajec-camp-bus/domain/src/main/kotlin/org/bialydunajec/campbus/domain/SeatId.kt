package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.AggregateId
import java.util.*

class SeatId(id: String = UUID.randomUUID().toString()) : AggregateId(id){

    companion object {
        private const val UNDEFINED = "UNDEFINED"
        fun undefined() = SeatId(UNDEFINED)
        fun new() = SeatId(UUID.randomUUID().toString())
    }
}
