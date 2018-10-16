package org.bialydunajec.registrations.domain.shirt.entity

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.shirt.valueobject.Color
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSize

// ENTITY
class ShirtOption(
        val color: Color,
        val size: ShirtSize,
        val pictureUrl: Url?
) {
}