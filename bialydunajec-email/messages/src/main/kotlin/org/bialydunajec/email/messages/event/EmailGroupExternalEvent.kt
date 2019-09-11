package org.bialydunajec.email.messages.event

sealed class EmailGroupExternalEvent {

    data class EmailGroupCreated(
            val aggregateId: String,
            val name: String
    ) : EmailGroupExternalEvent()


}