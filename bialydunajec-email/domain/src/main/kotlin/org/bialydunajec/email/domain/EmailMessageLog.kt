package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.valueobject.EmailStatus
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.*

//TODO: Oznaczac czy email zostal wyslany automatycznie czy utworzony recznie.

@Entity
@Table(schema = "email")
class EmailMessageLog(
        @Embedded
        private val recipient: EmailAddress,
        private val subject: String,
        private val content: String,
        emailMessageLogId: EmailMessageLogId
) : AuditableAggregateRoot<EmailMessageLogId, EmailMessageLogEvent>(emailMessageLogId), Versioned {

    init {
        registerEvent(
                EmailMessageLogEvent.EmailMessageCreated(
                        getAggregateId(),
                        recipient,
                        subject,
                        content,
                        getCreatedDate()
                )
        )
    }

    @Version
    private var version: Long? = null

    @Enumerated(EnumType.STRING)
    private var status: EmailStatus = EmailStatus.PENDING

    @Lob
    private var lastError: String? = null

    private var sentDate: ZonedDateTime? = null

    fun markAsSent(currentDateTime: ZonedDateTime) {
        status = EmailStatus.SENT
        sentDate = currentDateTime
        registerEvent(
                EmailMessageLogEvent.EmailMessageSentSuccess(getAggregateId(), currentDateTime)
        )
    }

    fun logSendingFailure(error: String) {
        status = EmailStatus.FAIL_TO_SEND
        lastError = error
        registerEvent(
                EmailMessageLogEvent.EmailMessageSentFailure(getAggregateId(), error)
        )
    }

    fun getRecipient() = recipient
    fun getSubject() = subject
    fun getContent() = content
    fun isSent() = status == EmailStatus.SENT

    override fun getVersion() = version
}