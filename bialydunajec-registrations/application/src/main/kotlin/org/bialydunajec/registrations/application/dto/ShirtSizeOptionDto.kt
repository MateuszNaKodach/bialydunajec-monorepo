package org.bialydunajec.registrations.application.dto


data class ShirtSizeOptionDto (
        val shirtSizeOptionId: String,
        val size: ShirtSizeDto,
        val available: Boolean = true
)