package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import javax.persistence.Embeddable

@Embeddable
class CottageId(campRegistrationsId: CampRegistrationsId, academicMinistryId: AcademicMinistryId)
    : AggregateId("$campRegistrationsId-$academicMinistryId")