package org.bialydunajec.ddd.application.base.query.api.dto

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.SocialMedia
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.GeoLocation
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription

fun Place.toDto() =
        PlaceDto(
                address = address.toDto(),
                geoLocation = geoLocation?.toDto()
        )

fun Address.toDto() =
        AddressDto(
                street = street.toString(),
                homeNumber = homeNumber.toString(),
                city = city.toString(),
                postalCode = postalCode.toString()
        )

fun GeoLocation.toDto() =
        GeoLocationDto(
                latitude = latitude,
                longitude = longitude
        )

fun ExtendedDescription.toDto() =
        ExtendedDescriptionDto(
                title = title,
                content = content
        )

fun SocialMedia.toDto() =
        SocialMediaDto(
                webPageUrl = webPage?.getUrl(),
                facebookPageUrl = facebookPage?.getUrl(),
                facebookGroupUrl = facebookGroup?.getUrl(),
                instagramUrl = instagram?.getUrl(),
                youTubeChannelUrl = youTubeChannel?.getUrl()
        )
