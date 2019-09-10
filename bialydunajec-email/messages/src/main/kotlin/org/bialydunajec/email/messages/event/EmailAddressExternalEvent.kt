package org.bialydunajec.email.messages.event

sealed class EmailAddressExternalEvent {

    data class EmailAddressCreated(
            val aggregateId: String,
            val emailAddressValue: String
    ) : EmailAddressExternalEvent()

    data class EmailAddressUpdated(
            val aggregateId: String,
            val emailAddressValue: String
    ) : EmailAddressExternalEvent()

    data class EmailAddressCatalogizedToEmailGroup(
            val aggregateId: String,
            val newEmailGroupId: String
    ) : EmailAddressExternalEvent()

}