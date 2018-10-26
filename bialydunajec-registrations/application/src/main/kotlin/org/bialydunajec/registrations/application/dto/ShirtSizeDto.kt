package org.bialydunajec.registrations.application.dto

import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtType

class ShirtSizeDto(
        val name: String,
        val type: ShirtType,
        val height: Double?,
        val width: Double?,
        val length: Double?
)