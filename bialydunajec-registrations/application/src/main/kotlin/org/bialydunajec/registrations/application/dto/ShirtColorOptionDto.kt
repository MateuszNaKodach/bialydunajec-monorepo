package org.bialydunajec.registrations.application.dto

data class ShirtColorOptionDto (
        val shirtSizeOptionId: String,
        val color: ColorDto,
        val available: Boolean = true
)