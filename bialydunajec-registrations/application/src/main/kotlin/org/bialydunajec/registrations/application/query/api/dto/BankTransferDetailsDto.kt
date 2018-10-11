package org.bialydunajec.registrations.application.query.api.dto


data class BankTransferDetailsDto(
        val accountNumber: String,
        val accountOwner: String,
        val accountOwnerAddress: String?,
        val transferTitleTemplate: String
)