package org.bialydunajec.registrations.domain.shirt.valueobject

import java.util.*
import javax.persistence.Embeddable

@Embeddable
class ShirtSize(
        val name: String,
        val width: Double?,
        val length: Double?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShirtSize

        return other.name == name || (other.length == length && other.width == width)
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), name, width, length)
    }
}