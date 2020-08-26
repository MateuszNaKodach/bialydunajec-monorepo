package org.bialydunajec.academicministry.domain

import org.bialydunajec.academicministry.domain.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.academicministry.domain.valueobject.AcademicPriestSnapshot
import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class AcademicMinistryEvent(override val aggregateId: AcademicMinistryId) : DomainEvent<AcademicMinistryId> {
    data class AcademicMinistryCreated(
            val snapshot: AcademicMinistrySnapshot
    ) : AcademicMinistryEvent(snapshot.academicMinistryId)

    data class AcademicMinistryUpdated(
            val snapshot: AcademicMinistrySnapshot
    ) : AcademicMinistryEvent(snapshot.academicMinistryId)

    data class AcademicMinistryPriestAssigned(
            val academicMinistryId: AcademicMinistryId,
            val snapshot: AcademicPriestSnapshot
    ) : AcademicMinistryEvent(academicMinistryId)

    data class AcademicMinistryPriestUpdated(
            val academicMinistryId: AcademicMinistryId,
            val snapshot: AcademicPriestSnapshot
    ) : AcademicMinistryEvent(academicMinistryId)

    data class AcademicMinistryPriestUnassigned(
            val academicMinistryId: AcademicMinistryId,
            val snapshot: AcademicPriestSnapshot
    ) : AcademicMinistryEvent(academicMinistryId)

    //data class AcademicMinistryInactivated(val academicMinistryId: AcademicMinistryId) : AcademicMinistryEvent(academicMinistryId)

    //data class AcademicMinistryActivated(val academicMinistryId: AcademicMinistryId) : AcademicMinistryEvent(academicMinistryId)

}
