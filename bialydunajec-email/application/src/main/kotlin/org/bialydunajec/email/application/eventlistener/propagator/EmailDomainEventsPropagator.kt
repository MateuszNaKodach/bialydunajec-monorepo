package org.bialydunajec.email.application.eventlistener.propagator

import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.bialydunajec.ddd.domain.extensions.toStringOrNull
import org.bialydunajec.email.domain.EmailEvent
import org.bialydunajec.email.domain.EmailGroupRepository
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class EmailDomainEventsPropagator(
    private val emailGroupRepository: EmailGroupRepository,
    private val externalEventBus: ExternalEventPublisher
) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: EmailEvent.EmailCatalogized) {
        with(domainEvent) {
            externalEventBus.send(
                EmailAddressExternalEvent.EmailCatalogized(
                    aggregateId.toString(),
                    emailAddress.toString(),
                    emailGroupId.toString(),
                    emailGroupRepository.findById(domainEvent.emailGroupId)?.name?.toStringOrNull(),
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
                    oldEmailAddress.toString(),
                    newEmailAddress.toString(),
                    emailGroupId.toString(),
                    emailGroupRepository.findById(domainEvent.emailGroupId)?.name?.toStringOrNull(),
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
                    emailGroupRepository.findById(domainEvent.emailGroupId)?.name?.toStringOrNull(),
                    emailOwner.firstName.toString(),
                    emailOwner.lastName.toString()
                )
            )
        }
    }

}
