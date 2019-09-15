package org.bialydunajec.email.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.email.domain.EmailAddressEvent
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
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
                            emailAddress.toString(),
                            isActive
                    )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailAddressEvent.EmailAddressCatalogizedToEmailGroup) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup(
                            aggregateId.toString(),
                            emailAddress.toString(),
                            newEmailGroupId.toString(),
                            newEmailGroup.name,
                            emailAddressOwner.firstName.toString(),
                            emailAddressOwner.lastName.toString()
                    )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailAddressEvent.EmailAddressDeactivated) {
        with(domainEvent) {
            externalEventBus.send(
                    EmailAddressExternalEvent.EmailAddressDeactivated(
                            aggregateId.toString(),
                            emailAddress.toString(),
                            emailGroupId.toString()
                    )
            )
        }
    }
}
