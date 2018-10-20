package org.bialydunajec.registrations.domain.shirt.valueobject

import javax.persistence.Embeddable

@Embeddable
data class Color(
        val name: String,
        val hexValue: Int?
)