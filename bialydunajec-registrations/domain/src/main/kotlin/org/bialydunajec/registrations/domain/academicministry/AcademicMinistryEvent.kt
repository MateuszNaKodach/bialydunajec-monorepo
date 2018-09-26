package org.bialydunajec.registrations.domain.academicministry

import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class AcademicMinistryEvent : DomainEvent<AcademicMinistryId>{
    data class AcademicMinistryCreated(override val aggregateId: AcademicMinistryId) : AcademicMinistryEvent()
}