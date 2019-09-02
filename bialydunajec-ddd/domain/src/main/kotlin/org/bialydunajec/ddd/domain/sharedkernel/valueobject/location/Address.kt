package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.validation.constraints.NotNull

@Embeddable
data class Address(
        @NotNull
        private val street: Street? = null,

        @Embedded
        private val homeNumber: HomeNumber? = null,

        @NotNull
        @Embedded
        private val city: CityName? = null,

        @NotNull
        @Embedded
        private val postalCode: PostalCode? = null
) : ValueObject {
        fun getStreet() = street
        fun getHomeNumber() = homeNumber
        fun getCity() = city
        fun getPostalCode() = postalCode
}