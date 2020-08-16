package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import java.time.ZonedDateTime

sealed class EmailMessageLogEvent : DomainEvent<EmailMessageLogId> {

    class EmailMessageCreated(
            override val aggregateId: EmailMessageLogId,
            val recipient: EmailAddress,
            val subject: String,
            val content: String,
            val createdDate: ZonedDateTime
    ) : EmailMessageLogEvent()

    class EmailMessageSentSuccess(
            override val aggregateId: EmailMessageLogId,
            val sentDate: ZonedDateTime
    ) : EmailMessageLogEvent()


    class EmailMessageSentFailure(
            override val aggregateId: EmailMessageLogId,
            val lastError: String
    ) : EmailMessageLogEvent()
}