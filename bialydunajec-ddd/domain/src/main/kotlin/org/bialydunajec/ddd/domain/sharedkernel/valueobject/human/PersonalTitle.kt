package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import org.bialydunajec.ddd.domain.base.validation.constraints.NullOrNotBlank
import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Embeddable
data class PersonalTitle(
        @NullOrNotBlank
        val name: String? = null,

        @NullOrNotBlank
        val prefix: String? = null,

        @NullOrNotBlank
        val postfix: String? = null
): ValueObject