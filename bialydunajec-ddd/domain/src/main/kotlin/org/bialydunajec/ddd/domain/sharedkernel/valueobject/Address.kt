package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class Address(
        @NotBlank
        val street: String,
        val number: String? = null,
        @NotBlank
        val city: String,
        @NotBlank
        val postalCode: String
) : ValueObject