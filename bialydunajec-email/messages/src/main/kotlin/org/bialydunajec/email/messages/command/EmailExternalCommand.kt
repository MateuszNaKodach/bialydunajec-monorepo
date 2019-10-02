package org.bialydunajec.email.messages.command

sealed class EmailExternalCommand {

    data class CatalogizeEmail(
        val emailAddress: String,
        val emailOwnerName: String,
        val emailOwnerLastName: String,
        val emailGroupId: String?
    ) : EmailExternalCommand()

    data class ChangeEmailAddress(
        val oldEmailAddress: String,
        val emailGroupId: String?,
        val newEmailAddress: String
    ) : EmailExternalCommand()

    data class CorrectEmailOwner(
        val emailAddress: String,
        val emailGroupId: String?,
        val emailOwnerName: String,
        val emailOwnerLastName: String
    ) : EmailExternalCommand()
}
