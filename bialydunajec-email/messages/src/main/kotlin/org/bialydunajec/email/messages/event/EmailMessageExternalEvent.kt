package org.bialydunajec.email.messages.event

import java.time.ZonedDateTime

sealed class EmailMessageExternalEvent {

    data class EmailMessageCreated(
            val emailMessageLogId: String,
            val recipientEmailAddress: String,
            val subject: String,
            val content: String,
            val createdDate: ZonedDateTime
    ) : EmailMessageExternalEvent()

    data class EmailMessageSentSuccess(
            val emailMessageLogId: String,
            val sentDate: ZonedDateTime
    ) : EmailMessageExternalEvent()

    data class EmailMessageSentFailure(
            val emailMessageLogId: String,
            val lastError: String
    ) : EmailMessageExternalEvent()
}