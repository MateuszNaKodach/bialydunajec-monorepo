package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Embeddable
data class PersonalTitle(
        @NotBlank
        val name: String,

        @NotEmpty
        val prefix: String? = null,

        @NotEmpty
        val postfix: String? = null
)