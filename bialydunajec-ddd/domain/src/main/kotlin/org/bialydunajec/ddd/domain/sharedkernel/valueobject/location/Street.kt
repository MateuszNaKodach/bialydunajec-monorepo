package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class Street(
        @NotBlank
        val street: String
){
        override fun toString() = street
}