package org.bialydunajec.ddd.base.dto


data class AddressDto(
        //@field:NullOrNotBlank
        val street: String? = null,

        //@field:NullOrNotBlank
        val homeNumber: String? = null,

        //@field:NullOrNotBlank
        val city: String? = null,

        //@field:NullOrNotBlank
        val postalCode: String? = null
)