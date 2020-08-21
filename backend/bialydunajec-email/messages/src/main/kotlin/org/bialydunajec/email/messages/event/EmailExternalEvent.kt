package org.bialydunajec.email.messages.event

sealed class EmailExternalEvent {

    data class EmailCatalogized(
        val emailId: String,
        val emailAddress: String,
        val emailGroupId: String,
        val emailGroupName: String?,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailExternalEvent()

    data class EmailOwnerCorrected(
        val emailId: String,
        val emailAddress: String,
        val emailGroupId: String,
        val emailGroupName: String?,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailExternalEvent()

    data class EmailAddressChanged(
        val emailId: String,
        val oldEmailAddress: String,
        val newEmailAddress: String,
        val emailGroupId: String,
        val emailGroupName: String?,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailExternalEvent()

}
