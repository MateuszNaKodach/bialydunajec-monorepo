package org.bialydunajec.email.messages.command

sealed class EmailAddressExternalCommand {
    data class CatalogizeEmailAddress(
            val emailAddressId: String,
            val emailAddress: String,
            val emailOwnerName: String,
            val emailOwnerLastName: String,
            val emailGroupName: String
    ) : EmailAddressExternalCommand()

    data class UpdateEmailAddress(
            val emailAddressId: String,
            val newEmailAddress: String
    ) : EmailAddressExternalCommand()
}