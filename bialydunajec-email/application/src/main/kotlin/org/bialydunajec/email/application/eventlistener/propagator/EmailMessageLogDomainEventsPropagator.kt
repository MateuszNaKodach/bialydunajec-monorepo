package org.bialydunajec.email.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.email.domain.EmailMessageLogEvent
import org.bialydunajec.email.messages.event.EmailMessageLogExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailMessageLogDomainEventsPropagator(private val externalEventBus: ExternalEventPublisher) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailMessageLogEvent.EmailMessageCreated) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailMessageLogExternalEvent.EmailMessageCreated(
                            aggregateId.toString(),
                            recipient.toString(),
                            subject,
                            content,
                            createdDate
                    )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailMessageLogEvent.EmailMessageSentSuccess) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailMessageLogExternalEvent.EmailMessageSentSuccess(aggregateId.toString(), sentDate)
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailMessageLogEvent.EmailMessageSentFailure) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailMessageLogExternalEvent.EmailMessageSentFailure(aggregateId.toString(), lastError)
            )
        }
    }

}
