package org.bialydunajec.ddd.application.base.query.api.dto

data class AddressDto(
        val street: String,
        val homeNumber: String? = null,
        val city: String,
        val postalCode: String? = null
)