package org.bialydunajec.ddd.domain.sharedkernel.valueobject.user

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class Username(
        @NotBlank
        private val username: String
): ValueObject {
        override fun toString() = username
}