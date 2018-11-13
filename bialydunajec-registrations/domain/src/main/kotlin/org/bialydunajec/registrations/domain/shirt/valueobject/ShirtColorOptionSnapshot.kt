package org.bialydunajec.registrations.domain.shirt.valueobject

import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId

data class ShirtColorOptionSnapshot (
        val shirtColorOptionId: ShirtColorOptionId,
        val color: Color,
        val available: Boolean = true
)