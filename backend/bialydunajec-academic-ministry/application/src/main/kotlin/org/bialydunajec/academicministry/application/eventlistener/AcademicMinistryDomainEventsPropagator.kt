package org.bialydunajec.academicministry.application.eventlistener

import org.bialydunajec.academicministry.domain.AcademicMinistryEvent
import org.bialydunajec.academicministry.messages.event.AcademicMinistryExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class AcademicMinistryDomainEventsPropagator(
        private val externalEventBus: ExternalEventPublisher
) {

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: AcademicMinistryEvent.AcademicMinistryCreated) {
        with(domainEvent.snapshot) {
            externalEventBus.send(
                    AcademicMinistryExternalEvent.AcademicMinistryCreated(
                            academicMinistryId = this.academicMinistryId.toString(),
                            officialName = this.officialName,
                            shortName = this.shortName,
                            logoImageUrl = this.logoImageUrl.toString()
                    )
            )
        }
    }

    @Async
    @TransactionalEventListener
    fun handleDomainEvent(domainEvent: AcademicMinistryEvent.AcademicMinistryUpdated) {
        with(domainEvent.snapshot) {
            externalEventBus.send(
                    AcademicMinistryExternalEvent.AcademicMinistryUpdated(
                            academicMinistryId = this.academicMinistryId.toString(),
                            officialName = this.officialName,
                            shortName = this.shortName,
                            logoImageUrl = this.logoImageUrl.toString()
                    )
            )
        }

    }
}
