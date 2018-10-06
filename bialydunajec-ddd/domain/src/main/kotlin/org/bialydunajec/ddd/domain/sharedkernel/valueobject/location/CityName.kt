package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class CityName(
        @NotBlank
        val city: String
){
        override fun toString() = city
}