package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.AggregateId
import java.util.*

class SeatId(id: String = UUID.randomUUID().toString()) : AggregateId(id){

    companion object {
        private const val UNDEFINED = "UNDEFINED"
        fun undefined() = SeatId(UNDEFINED)
        fun new() = SeatId(UUID.randomUUID().toString())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }


}
