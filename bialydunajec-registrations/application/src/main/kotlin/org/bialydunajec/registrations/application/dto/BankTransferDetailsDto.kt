package org.bialydunajec.registrations.application.dto

import org.bialydunajec.ddd.domain.base.validation.constraints.NullOrNotBlank


data class BankTransferDetailsDto(
        @field:NullOrNotBlank
        val accountNumber: String?,

        @field:NullOrNotBlank
        val accountOwner: String?,

        @field:NullOrNotBlank
        val accountOwnerAddress: String?,

        @field:NullOrNotBlank
        val transferTitleTemplate: String?
)