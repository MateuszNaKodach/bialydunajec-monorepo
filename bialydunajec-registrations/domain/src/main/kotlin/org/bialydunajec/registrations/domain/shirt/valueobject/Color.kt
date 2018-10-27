package org.bialydunajec.registrations.domain.shirt.valueobject

import java.util.*
import javax.persistence.Embeddable

@Embeddable
class Color(
        val name: String,
        val hexValue: String?
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Color

        return other.name == name || (hexValue!=null && other.hexValue == hexValue)
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), name, hexValue)
    }
}