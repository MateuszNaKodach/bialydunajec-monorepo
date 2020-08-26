package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class Street(
        @NotBlank
        private val street: String
): ValueObject {
        override fun toString() = street
}
