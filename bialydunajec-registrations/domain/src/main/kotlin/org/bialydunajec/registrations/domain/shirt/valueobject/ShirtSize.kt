package org.bialydunajec.registrations.domain.shirt.valueobject

import javax.persistence.Embeddable

@Embeddable
data class ShirtSize(
        val name: String,
        val width: Double,
        val length: Double
){

}