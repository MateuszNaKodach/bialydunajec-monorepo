package org.bialydunajec.email.application.eventlistener

import org.bialydunajec.ddd.application.base.external.event.ExternalEventBus
import org.bialydunajec.email.domain.EmailMessageLogEvent
import org.bialydunajec.email.messages.event.EmailMessageExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailMessageLogDomainEventsPropagator(private val externalEventBus: ExternalEventBus) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailMessageLogEvent.EmailMessageCreated) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailMessageExternalEvent.EmailMessageCreated(aggregateId.toString(), recipient.toString(), subject, content)
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailMessageLogEvent.EmailMessageSentSuccess) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailMessageExternalEvent.EmailMessageSentSuccess(aggregateId.toString(), sentDate)
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailMessageLogEvent.EmailMessageSentFailure) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailMessageExternalEvent.EmailMessageSentFailure(aggregateId.toString(), lastError)
            )
        }
    }

}