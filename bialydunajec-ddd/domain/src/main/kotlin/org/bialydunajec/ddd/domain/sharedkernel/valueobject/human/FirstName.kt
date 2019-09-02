package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class FirstName(
        @NotBlank
        private val firstName: String
): ValueObject{
        override fun toString() = firstName
}