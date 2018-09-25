package org.bialydunajec.ddd.domain.valueobject

import java.util.*
import javax.persistence.Embeddable

@Embeddable
abstract class AggregateId(val uuid: String = UUID.randomUUID().toString()) : Identifier{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateId

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}