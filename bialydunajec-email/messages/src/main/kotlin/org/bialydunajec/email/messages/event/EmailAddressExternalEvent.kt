package org.bialydunajec.email.messages.event

sealed class EmailAddressExternalEvent {

    data class EmailCatalogized(
        val emailId: String,
        val emailAddress: String,
        val emailGroupId: String,
        val emailGroupName: String?,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailAddressExternalEvent()

    data class EmailOwnerCorrected(
        val emailId: String,
        val emailAddress: String,
        val emailGroupId: String,
        val emailGroupName: String?,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailAddressExternalEvent()

    //TODO: Add info about new
    data class EmailAddressChanged(
        val emailId: String,
        val oldEmailAddress: String,
        val newEmailAddress: String,
        val emailGroupId: String,
        val emailGroupName: String?,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailAddressExternalEvent()

}
