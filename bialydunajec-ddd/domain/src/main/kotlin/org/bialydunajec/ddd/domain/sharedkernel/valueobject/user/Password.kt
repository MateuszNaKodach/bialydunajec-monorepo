package org.bialydunajec.ddd.domain.sharedkernel.valueobject.user

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class Password(
        @NotBlank
        val password: String
) {
    override fun toString() = password
}