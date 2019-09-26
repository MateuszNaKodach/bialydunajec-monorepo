package org.bialydunajec.email.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.email.domain.EmailAddressEvent
import org.bialydunajec.email.domain.EmailGroupEvent
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.bialydunajec.email.messages.event.EmailGroupExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailGroupDomainEventsPropagator(private val externalEventBus: ExternalEventPublisher) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailGroupEvent.EmailGroupCreated) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailGroupExternalEvent.EmailGroupCreated(
                            aggregateId.toString(),
                            emailGroup.emailAddressGroup.name
                    )
            )
        }
    }
}
