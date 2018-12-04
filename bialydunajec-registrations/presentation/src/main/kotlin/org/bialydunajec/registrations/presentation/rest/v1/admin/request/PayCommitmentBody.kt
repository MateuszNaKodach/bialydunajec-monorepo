package org.bialydunajec.registrations.presentation.rest.v1.admin.request

data class PayCommitmentBody (
        val campParticipantCottageAccountId: String,
        val useAccountFunds: Boolean = false
)