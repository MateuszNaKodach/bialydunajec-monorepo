package org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class PhoneNumber(
        @Column(name = "phone_number")
        @NotBlank
        private val number: String
) : ValueObject {

    override fun toString() = number
}
