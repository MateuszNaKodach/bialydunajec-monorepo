package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class PostalCode(
        @NotBlank
        val postalCode: String
) : ValueObject {
    override fun toString() = postalCode
}