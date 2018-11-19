package org.bialydunajec.registrations.domain.campbus.valueobject

import javax.persistence.*
import javax.validation.Valid

@Embeddable
class CampBusTimetable(

        @Valid
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "dateTime", column = Column(name = "origin_departureDate")),
                AttributeOverride(name = "place.address.street.street", column = Column(name = "origin_street")),
                AttributeOverride(name = "place.address.homeNumber.homeNumber", column = Column(name = "origin_number")),
                AttributeOverride(name = "place.address.city.city", column = Column(name = "origin_city")),
                AttributeOverride(name = "place.address.postalCode.postalCode", column = Column(name = "origin_postalCode")),
                AttributeOverride(name = "place.geoLocation.latitude", column = Column(name = "origin_geo_latitude")),
                AttributeOverride(name = "place.geoLocation.longitude", column = Column(name = "origin_geo_longitude")),
                AttributeOverride(name = "notes", column = Column(name = "origin_notes"))
        )
        var origin: CampBusStop,

        @Valid
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "dateTime", column = Column(name = "destination_arrivalDate")),
                AttributeOverride(name = "place.address.street.street", column = Column(name = "destination_street")),
                AttributeOverride(name = "place.address.homeNumber.homeNumber", column = Column(name = "destination_number")),
                AttributeOverride(name = "place.address.city.city", column = Column(name = "destination_city")),
                AttributeOverride(name = "place.address.postalCode.postalCode", column = Column(name = "destination_postalCode")),
                AttributeOverride(name = "place.geoLocation.latitude", column = Column(name = "destination_geo_latitude")),
                AttributeOverride(name = "place.geoLocation.longitude", column = Column(name = "destination_geo_longitude")),
                AttributeOverride(name = "notes", column = Column(name = "destination_notes"))
        )
        var destination: CampBusStop
) {

    fun isFullyPlanned() = origin.isPlanned() && destination.isPlanned()

}