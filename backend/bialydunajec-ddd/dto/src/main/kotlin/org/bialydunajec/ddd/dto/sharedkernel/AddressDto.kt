package org.bialydunajec.ddd.dto.sharedkernel


data class AddressDto(
        val street: String? = null,

        val homeNumber: String? = null,

        val city: String? = null,

        val postalCode: String? = null
)
