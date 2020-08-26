package org.bialydunajec.ddd.application.base.dto

import org.bialydunajec.ddd.dto.sharedkernel.*
import org.bialydunajec.ddd.domain.sharedkernel.extensions.toStringOrNull
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing.Audit
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing.Auditor
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.AgeRange
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.*
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.*
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription
import java.time.ZoneId

fun Gender.toDto() =
        GenderDto.values().find { it.name == name }!!

fun GenderDto.toValueObject() =
        Gender.values().find { it.name == name }!!

fun Place.toDto() =
        PlaceDto(
                address = address.toDto(),
                geoLocation = geoLocation?.toDto()
        )

fun PlaceDto.toValueObject() =
        Place(
                address = address.toValueObject(),
                geoLocation = geoLocation?.toValueObject()
        )


fun Address.toDto() =
        AddressDto(
                street = street.toStringOrNull(),
                homeNumber = homeNumber.toStringOrNull(),
                city = city.toStringOrNull(),
                postalCode = postalCode.toStringOrNull()
        )

fun AddressDto.toValueObject() =
        Address(
                street = street?.let { Street(it) },
                homeNumber = homeNumber?.let { HomeNumber(it) },
                city = city?.let { CityName(it) },
                postalCode = postalCode?.let { PostalCode(it) }
        )

fun GeoLocation.toDto() =
        GeoLocationDto(
                latitude = latitude,
                longitude = longitude
        )

fun GeoLocationDto.toValueObject() =
        GeoLocation(latitude, longitude)

fun ExtendedDescription.toDto() =
        ExtendedDescriptionDto(
                title = title,
                content = content
        )

fun ExtendedDescriptionDto.toValueObject() =
        ExtendedDescription(title, content)

fun SocialMedia.toDto() =
        SocialMediaDto(
                webPageUrl = webPage?.getUrl(),
                facebookPageUrl = facebookPage?.getUrl(),
                facebookGroupUrl = facebookGroup?.getUrl(),
                instagramUrl = instagram?.getUrl(),
                youTubeChannelUrl = youTubeChannel?.getUrl()
        )

fun SocialMediaDto.toValueObject() = SocialMedia(
        webPage = webPageUrl?.let { WebPage(it) },
        facebookPage = facebookPageUrl?.let { FacebookPage(it) },
        facebookGroup = facebookGroupUrl?.let { FacebookGroup(it) },
        instagram = instagramUrl?.let { Instagram(it) },
        youTubeChannel = youTubeChannelUrl?.let { YouTubeChannel(it) }
)

fun AgeRange.toDto() =
        AgeRangeDto(min, max)

fun AgeRangeDto.toValueObject() =
        AgeRange(min, max)

fun Audit.toDto() =
        AuditDto(createdDate.atZone(ZoneId.systemDefault()), createdBy?.toDto(), lastModifiedDate?.atZone(ZoneId.systemDefault()), lastModifiedBy?.toDto())

fun Auditor.toDto() =
        AuditorDto(auditorId.toStringOrNull())

