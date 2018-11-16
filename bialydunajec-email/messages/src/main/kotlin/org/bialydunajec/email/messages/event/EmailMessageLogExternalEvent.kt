package org.bialydunajec.email.messages.event

import java.time.ZonedDateTime

sealed class EmailMessageLogExternalEvent {

    data class EmailMessageCreated(
            val emailMessageLogId: String,
            val recipientEmailAddress: String,
            val subject: String,
            val content: String,
            val createdDate: ZonedDateTime
    ) : EmailMessageLogExternalEvent()

    data class EmailMessageSentSuccess(
            val emailMessageLogId: String,
            val sentDate: ZonedDateTime
    ) : EmailMessageLogExternalEvent()

    data class EmailMessageSentFailure(
            val emailMessageLogId: String,
            val lastError: String
    ) : EmailMessageLogExternalEvent()
}