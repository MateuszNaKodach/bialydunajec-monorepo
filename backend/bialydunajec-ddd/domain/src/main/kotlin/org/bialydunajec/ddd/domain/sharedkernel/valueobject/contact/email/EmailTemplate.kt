package org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
class EmailTemplate(
        @NotBlank
        val title: String,

        @NotBlank
        val content: String
) : ValueObject
