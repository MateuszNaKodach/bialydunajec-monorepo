package org.bialydunajec.email.messages.command

sealed class EmailExternalCommand {

    //TODO: Jak handlować ALL grupę? Czy zrobić taką i dodawać do niej zawsze? Czy w readmodelu każdy robić? Albo czy umożliwić wiele grup tutaj?
    //Być może to i to - bo możemy chcieć mieć grupę obozowiczów danej edycji i wszystkich - można to ogarnać na read modelu!?
    //Grupa bardziej jak tag, z nazwą?
    //Raczej tak - bo będzie grupa, uczestnicy obozu i uczestnicy z danej chatki. - Czy powinny się zagnieżdżać?
    data class CatalogizeEmail(
        val emailAddress: String,
        val emailOwnerName: String,
        val emailOwnerLastName: String,
        val emailGroupId: String?
    ) : EmailExternalCommand() {

        fun alsoWithGroups(emailGroupIds: Set<String>): List<CatalogizeEmail> =
            listOf(this).plus(emailGroupIds.map { this.copy(emailGroupId = it) })

        fun alsoWithGroup(emailGroupId: String): List<CatalogizeEmail> =
            listOf(this).plus(this.copy(emailGroupId = emailGroupId))
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
