package org.bialydunajec.ddd.domain.sharedkernel.valueobject.human

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class LastName(
        @NotBlank
        val lastName: String
)