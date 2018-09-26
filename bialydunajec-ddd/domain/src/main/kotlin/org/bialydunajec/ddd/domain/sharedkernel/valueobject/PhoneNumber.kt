package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class PhoneNumber(
        @Column(name = "phone_number")
        @NotBlank
        val number: String
) : ValueObject {

    override fun toString() = number
}