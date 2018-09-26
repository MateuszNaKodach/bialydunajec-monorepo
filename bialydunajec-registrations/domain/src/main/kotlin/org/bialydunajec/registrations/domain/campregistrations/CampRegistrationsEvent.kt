package org.bialydunajec.registrations.domain.campregistrations

import org.bialydunajec.ddd.domain.base.event.DomainEvent


sealed class CampRegistrationsEvent : DomainEvent<CampRegistrationsId> {
    data class CampRegistrationsCreated(override val aggregateId: CampRegistrationsId) : CampRegistrationsEvent()
}