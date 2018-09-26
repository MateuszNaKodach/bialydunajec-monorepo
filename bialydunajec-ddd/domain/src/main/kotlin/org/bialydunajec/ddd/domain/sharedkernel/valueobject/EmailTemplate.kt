package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
class EmailTemplate(
        @NotBlank
        val title: String,

        @NotBlank
        val content: String
)