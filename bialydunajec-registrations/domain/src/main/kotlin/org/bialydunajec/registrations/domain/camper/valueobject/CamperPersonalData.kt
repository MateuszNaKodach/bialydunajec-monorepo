package org.bialydunajec.registrations.domain.camper.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.*
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Embeddable
data class CamperPersonalData constructor(
        @NotNull
        @Embedded
        private val firstName: FirstName,

        @NotNull
        @Embedded
        private val lastName: LastName,

        @NotBlank
        @Enumerated(EnumType.STRING)
        private val gender: Gender,

        @NotNull
        @Embedded
        private val pesel: Pesel? = null,

        @NotNull
        @Embedded
        private val birthDate: BirthDate? = pesel?.getBirthDate()
) {

    companion object {
        fun withPeselNumber(firstName: FirstName, lastName: LastName, gender: Gender, pesel: Pesel) = CamperPersonalData(
                firstName = firstName,
                lastName = lastName,
                gender = gender,
                pesel = pesel,
                birthDate = pesel.getBirthDate()
        )

        fun withoutPeselNumber(firstName: FirstName, lastName: LastName, gender: Gender, birthDate: BirthDate) = CamperPersonalData(
                firstName = firstName,
                lastName = lastName,
                gender = gender,
                birthDate = birthDate
        )
    }

    fun getFirstName() = firstName
    fun getLastName() = lastName
    fun getGender() = gender
    fun getPesel() = pesel
    fun getBirthDate() = birthDate
}