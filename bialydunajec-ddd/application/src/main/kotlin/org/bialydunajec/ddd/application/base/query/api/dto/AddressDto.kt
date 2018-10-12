package org.bialydunajec.ddd.application.base.query.api.dto

data class AddressDto(
        val street: String? = null,
        val homeNumber: String? = null,
        val city: String? = null,
        val postalCode: String? = null
)