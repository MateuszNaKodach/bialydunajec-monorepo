package org.bialydunajec.ddd.dto.sharedkernel

data class PlaceDto(
        val address: AddressDto,
        val geoLocation: GeoLocationDto?
)
