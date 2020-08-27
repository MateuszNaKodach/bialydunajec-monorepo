package org.bialydunajec.academicministry.domain.entity

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.EntityId
import javax.persistence.Embeddable

@Embeddable
class AcademicPriestId(academicPriestId: String = defaultValue()) : EntityId(academicPriestId)
