package org.bialydunajec.ddd.domain.sharedkernel.valueobject.user

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class Password(
        @NotBlank
        val password: String
): ValueObject {
    override fun toString() = password
}
