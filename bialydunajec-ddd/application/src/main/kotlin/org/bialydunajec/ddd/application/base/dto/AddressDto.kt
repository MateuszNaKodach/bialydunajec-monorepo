package org.bialydunajec.ddd.application.base.dto

import org.bialydunajec.ddd.domain.base.validation.constraints.NullOrNotBlank

data class AddressDto(
        @field:NullOrNotBlank
        val street: String? = null,

        @field:NullOrNotBlank
        val homeNumber: String? = null,

        @field:NullOrNotBlank
        val city: String? = null,

        @field:NullOrNotBlank
        val postalCode: String? = null
)