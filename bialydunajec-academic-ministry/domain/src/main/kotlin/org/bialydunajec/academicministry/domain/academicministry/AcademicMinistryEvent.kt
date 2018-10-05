package org.bialydunajec.academicministry.domain.academicministry

import org.bialydunajec.academicministry.domain.academicministry.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class AcademicMinistryEvent(override val aggregateId: AcademicMinistryId) : DomainEvent<AcademicMinistryId> {
    data class AcademicMinistryCreated(
            val snapshot: AcademicMinistrySnapshot
    ) : AcademicMinistryEvent(snapshot.academicMinistryId)

    data class AcademicMinistryUpdated(
            val snapshot: AcademicMinistrySnapshot
    ) : AcademicMinistryEvent(snapshot.academicMinistryId)

    //data class AcademicMinistryDeleted(val academicMinistryId: AcademicMinistryId) : AcademicMinistryEvent(academicMinistryId)
}