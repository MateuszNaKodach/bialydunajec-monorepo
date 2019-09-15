package org.bialydunajec.email.messages.event

sealed class EmailAddressExternalEvent {

    data class EmailAddressCreated(
            val emailId: String,
            val emailAddress: String,
            val isActive: Boolean
    ) : EmailAddressExternalEvent()

    data class EmailAddressCatalogizedToEmailGroup(
            val emailId: String,
            val emailAddress: String,
            val newEmailGroupId: String,
            val emailGroupName: String,
            val ownerFirstName: String,
            val ownerLastName: String
    ) : EmailAddressExternalEvent()

    data class EmailAddressDeactivated(
            val emailId: String,
            val emailAddress: String,
            val emailGroupId: String?
    ) : EmailAddressExternalEvent()

}