package org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Embeddable
data class EmailAddress(
        @Email
        @NotBlank
        private val email: String
) : ValueObject {

    override fun toString() = email
}
