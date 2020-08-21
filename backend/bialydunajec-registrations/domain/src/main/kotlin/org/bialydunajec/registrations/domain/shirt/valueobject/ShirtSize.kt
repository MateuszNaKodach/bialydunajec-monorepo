package org.bialydunajec.registrations.domain.shirt.valueobject

import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class ShirtSize(
        @Column(name = "size_name")
        val name: String,
        @Enumerated(EnumType.STRING)
        val type: ShirtType,
        val height: Double?,
        val width: Double? = null,
        val length: Double? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShirtSize

        return other.name == name && other.type == type || (other.type == type && other.length == length && other.width == width && other.height == height)
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), name, width, length)
    }
}