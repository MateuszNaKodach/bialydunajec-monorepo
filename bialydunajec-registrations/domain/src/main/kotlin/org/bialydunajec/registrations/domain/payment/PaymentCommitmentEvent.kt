package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class PaymentCommitmentEvent : DomainEvent<PaymentCommitmentId> {
    data class Created(
            override val aggregateId: PaymentCommitmentId
    ) : PaymentCommitmentEvent()
}