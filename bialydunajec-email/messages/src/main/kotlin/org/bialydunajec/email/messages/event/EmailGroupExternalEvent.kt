package org.bialydunajec.email.messages.event

sealed class EmailGroupExternalEvent {

    data class EmailGroupCreated(
            val emailGroupId: String,
            val name: String?
    ) : EmailGroupExternalEvent()

    data class EmailGroupNameChanged(
        val emailGroupId: String,
        val name: String
    ) : EmailGroupExternalEvent()

}
