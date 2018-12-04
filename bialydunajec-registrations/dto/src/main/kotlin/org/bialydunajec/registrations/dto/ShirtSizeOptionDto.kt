package org.bialydunajec.registrations.dto


data class ShirtSizeOptionDto (
        val shirtSizeOptionId: String,
        val size: ShirtSizeDto,
        val available: Boolean = true
)