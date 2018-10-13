package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class HomeNumber(
        @NotBlank
        val homeNumber: String
): ValueObject {
        override fun toString() = homeNumber
}