package org.bialydunajec.email.application.eventlistener

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.email.domain.EmailAddressEvent
import org.bialydunajec.email.domain.EmailMessageLogEvent
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.bialydunajec.email.messages.event.EmailMessageLogExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailAddressDomainEventsPropagator(private val externalEventBus: ExternalEventPublisher) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailAddressEvent.EmailAddressCreated) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailAddressExternalEvent.EmailAddressCreated(
                            aggregateId.toString(),
                            emailAddressValue.toString()
                    )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailAddressEvent.EmailAddressUpdated) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailAddressExternalEvent.EmailAddressUpdated(
                            aggregateId.toString(),
                            emailAddressValue.toString()
                    )
            )
        }
    }
}
