package org.bialydunajec.ddd.base.dto

data class PlaceDto(
        val address: AddressDto,
        val geoLocation: GeoLocationDto?
)