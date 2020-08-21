package org.bialydunajec.campbus.domain

data class Address(
        val street: Street? = null,

        val homeNumber: HomeNumber? = null,

        val city: CityName? = null,

        val postalCode: PostalCode? = null
) {


    data class Street(
            val street: String
    ) {
        override fun toString() = street
    }

    data class HomeNumber(
            val homeNumber: String
    ) {
        override fun toString() = homeNumber
    }

    data class CityName(
            val city: String
    ) {
        override fun toString() = city
    }

    data class PostalCode(
            val postalCode: String
    ) {
        override fun toString() = postalCode
    }

}
