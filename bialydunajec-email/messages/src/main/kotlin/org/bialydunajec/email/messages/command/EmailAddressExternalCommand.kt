package org.bialydunajec.email.messages.command

sealed class EmailAddressExternalCommand {
    data class CatalogizeEmailAddress(
            val emailAddress: String,
            val emailGroupName: String
    ) : EmailAddressExternalCommand()

    data class UpdateEmailAddress(
            val oldEmailAddress: String,
            val newEmailAddress: String
    ) : EmailAddressExternalCommand()
}