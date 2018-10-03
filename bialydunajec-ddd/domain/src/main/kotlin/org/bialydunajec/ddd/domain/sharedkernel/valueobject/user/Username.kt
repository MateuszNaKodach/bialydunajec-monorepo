package org.bialydunajec.ddd.domain.sharedkernel.valueobject.user

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class Username(
        @NotBlank
        private val username: String
){
        override fun toString() = username
}