package org.bialydunajec.registrations.domain.academicministry.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId

class AcademicMinistrySnapshot internal constructor(
        val academicMinistryId: AcademicMinistryId,
        val officialName: String,
        val shortName: String?,
        val logoImageUrl: Url?
)