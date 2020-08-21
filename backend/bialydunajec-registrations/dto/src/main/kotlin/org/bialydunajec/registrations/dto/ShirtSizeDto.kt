package org.bialydunajec.registrations.dto

class ShirtSizeDto(
        val name: String,
        val type: ShirtTypeDto,
        val height: Double?,
        val width: Double?,
        val length: Double?
)