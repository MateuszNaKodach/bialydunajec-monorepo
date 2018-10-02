package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class HomeNumber(
        @NotBlank
        val homeNumber: String
)