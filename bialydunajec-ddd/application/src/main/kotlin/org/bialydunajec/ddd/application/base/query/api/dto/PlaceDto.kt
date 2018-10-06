package org.bialydunajec.ddd.application.base.query.api.dto

data class PlaceDto(
        val address: AddressDto,
        val geoLocation: GeoLocationDto?
)