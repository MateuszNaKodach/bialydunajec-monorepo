package org.bialydunajec.email.messages.event

sealed class EmailAddressExternalEvent {

    data class EmailCatalogized(
        val emailId: String,
        val emailAddress: String,
        val emailGroupId: String,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailAddressExternalEvent()

    data class EmailOwnerCorrected(
        val emailId: String,
        val emailAddress: String,
        val emailGroupId: String,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailAddressExternalEvent()

    data class EmailAddressChanged(
        val emailId: String,
        val emailAddress: String,
        val emailGroupId: String,
        val ownerFirstName: String,
        val ownerLastName: String
    ) : EmailAddressExternalEvent()

}
