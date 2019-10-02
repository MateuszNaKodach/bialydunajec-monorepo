package org.bialydunajec.email.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.email.domain.EmailEvent
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailDomainEventsPropagator(private val externalEventBus: ExternalEventPublisher) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailEvent.EmailCatalogized) {
        with(domainEvent) {
            externalEventBus.send(
                EmailAddressExternalEvent.EmailCatalogized(
                    aggregateId.toString(),
                    emailAddress.toString(),
                    emailGroupId.toString(),
                    emailOwner.firstName.toString(),
                    emailOwner.lastName.toString()
                )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailEvent.EmailAddressChanged) {
        with(domainEvent) {
            externalEventBus.send(
                EmailAddressExternalEvent.EmailAddressChanged(
                    aggregateId.toString(),
                    emailAddress.toString(),
                    emailGroupId.toString(),
                    emailOwner.firstName.toString(),
                    emailOwner.lastName.toString()
                )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailEvent.EmailOwnerCorrected) {
        with(domainEvent) {
            externalEventBus.send(
                EmailAddressExternalEvent.EmailOwnerCorrected(
                    aggregateId.toString(),
                    emailAddress.toString(),
                    emailGroupId.toString(),
                    emailOwner.firstName.toString(),
                    emailOwner.lastName.toString()
                )
            )
        }
    }

}
