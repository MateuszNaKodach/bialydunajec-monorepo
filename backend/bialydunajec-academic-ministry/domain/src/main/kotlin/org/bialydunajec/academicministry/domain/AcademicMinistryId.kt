package org.bialydunajec.academicministry.domain

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class AcademicMinistryId(academicMinistryId: String? = null) : AggregateId(academicMinistryId ?: defaultValue())
