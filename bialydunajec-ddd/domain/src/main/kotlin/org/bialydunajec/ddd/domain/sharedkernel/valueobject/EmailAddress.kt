package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Embeddable
data class EmailAddress(
        @Email
        @NotBlank
        val email: String
) : ValueObject {

    override fun toString() = email
}