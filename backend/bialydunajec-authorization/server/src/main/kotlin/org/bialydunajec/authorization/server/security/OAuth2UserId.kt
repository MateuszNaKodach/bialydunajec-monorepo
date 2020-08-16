package org.bialydunajec.authorization.server.security

import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable

@Embeddable
class OAuth2UserId(private val aggregateId: String = defaultValue()) : Serializable {

    override fun toString() = aggregateId

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OAuth2UserId

        if (aggregateId != other.aggregateId) return false

        return true
    }

    override fun hashCode(): Int {
        return aggregateId.hashCode()
    }

    companion object {
        fun defaultValue() = UUID.randomUUID().toString()
    }
}