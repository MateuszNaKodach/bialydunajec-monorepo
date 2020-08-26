package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class HomeNumber(
        @NotBlank
        private val homeNumber: String
): ValueObject {
        override fun toString() = homeNumber
}
