package org.bialydunajec.eventsourcing.domain

class AggregateVersion(private val version: Long) {
    companion object {
        val ZERO = AggregateVersion(0)
    }

    fun increase() = AggregateVersion(version + 1)

    fun toLong() = version

    override fun toString(): String = version.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateVersion

        if (version != other.version) return false

        return true
    }

    override fun hashCode(): Int {
        return version.hashCode()
    }


}