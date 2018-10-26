package org.bialydunajec.ddd.application.base.dto

data class PlaceDto(
        val address: AddressDto,
        val geoLocation: GeoLocationDto?
)