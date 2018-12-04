package org.bialydunajec.registrations.dto


data class BankTransferDetailsDto(
        //@field:NullOrNotBlank
        val accountNumber: String?,

        //@field:NullOrNotBlank
        val accountOwner: String?,

        //@field:NullOrNotBlank
        val accountOwnerAddress: String?,

        //@field:NullOrNotBlank
        val transferTitleTemplate: String?
)