package org.bialydunajec.registrations.domain.campbus.valueobject

import java.util.Objects.nonNull
import javax.persistence.*
import javax.validation.Valid

@Embeddable
internal class CampBusTimetable(

        @Valid
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "when", column = Column(name = "departureDate")),
                AttributeOverride(name = "address.street", column = Column(name = "origin_street")),
                AttributeOverride(name = "address.number", column = Column(name = "origin_number")),
                AttributeOverride(name = "address.city", column = Column(name = "origin_city")),
                AttributeOverride(name = "address.postalCode", column = Column(name = "origin_postalCode")),
                AttributeOverride(name = "geoLocation.latitude", column = Column(name = "origin_geo_latitude")),
                AttributeOverride(name = "geoLocation.longitude", column = Column(name = "origin_geo_longitude"))
        )
        var origin: CampBusStop,

        @Valid
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "when", column = Column(name = "arrivalDate")),
                AttributeOverride(name = "address.street", column = Column(name = "destination_street")),
                AttributeOverride(name = "address.number", column = Column(name = "destination_number")),
                AttributeOverride(name = "address.city", column = Column(name = "destination_city")),
                AttributeOverride(name = "address.postalCode", column = Column(name = "destination_postalCode")),
                AttributeOverride(name = "geoLocation.latitude", column = Column(name = "destination_geo_latitude")),
                AttributeOverride(name = "geoLocation.longitude", column = Column(name = "destination_geo_longitude"))
        )
        var destination: CampBusStop
) {

    fun isFullyPlanned() = origin.isPlanned() && destination.isPlanned()

}