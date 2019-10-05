package org.bialydunajec.email.messages.command

sealed class EmailExternalCommand {

    data class CatalogizeEmail(
        val emailAddress: String,
        val emailOwnerName: String,
        val emailOwnerLastName: String,
        val emailGroupId: String?
    ) : EmailExternalCommand() {

        fun alsoWithGroups(emailGroupIds: Set<String>): Set<CatalogizeEmail> =
            listOf(this).plus(emailGroupIds.map { this.copy(emailGroupId = it) }).toSet()

        fun alsoWithGroup(emailGroupId: String): Set<CatalogizeEmail> =
            listOf(this).plus(this.copy(emailGroupId = emailGroupId)).toSet()
    }

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
