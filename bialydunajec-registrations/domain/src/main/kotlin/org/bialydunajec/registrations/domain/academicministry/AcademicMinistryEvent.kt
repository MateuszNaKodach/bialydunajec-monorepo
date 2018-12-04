package org.bialydunajec.registrations.domain.academicministry

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.academicministry.valueobject.AcademicMinistrySnapshot

sealed class AcademicMinistryEvent : DomainEvent<AcademicMinistryId> {
    data class AcademicMinistryCreated(
            override val aggregateId: AcademicMinistryId,
            val snapshot: AcademicMinistrySnapshot
    ) : AcademicMinistryEvent()

    data class AcademicMinistryUpdated(
            override val aggregateId: AcademicMinistryId,
            val snapshot: AcademicMinistrySnapshot
    ) : AcademicMinistryEvent()

}