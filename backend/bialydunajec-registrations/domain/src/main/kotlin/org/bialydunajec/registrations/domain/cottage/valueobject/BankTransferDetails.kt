package org.bialydunajec.registrations.domain.cottage.valueobject

import org.hibernate.validator.constraints.Length
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
data class BankTransferDetails(
        @NotBlank
        @Length(min = 26, max = 26)
        val accountNumber: String?,

        @NotBlank
        val accountOwner: String?,

        @NotBlank
        val accountOwnerAddress: String?,

        val transferTitleTemplate: String?
)