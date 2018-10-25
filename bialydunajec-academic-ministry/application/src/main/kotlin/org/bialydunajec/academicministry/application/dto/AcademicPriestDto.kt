package org.bialydunajec.academicministry.application.dto

import org.bialydunajec.academicministry.domain.valueobject.AcademicPriestSnapshot
import org.bialydunajec.ddd.application.base.query.api.dto.ExtendedDescriptionDto
import org.bialydunajec.ddd.application.base.query.api.dto.toDto
import org.bialydunajec.ddd.domain.extensions.toStringOrNull

data class AcademicPriestDto(
        val academicPriestId: String,
        val firstName: String,
        val lastName: String,
        val personalTitle: PersonalTitleDto?,
        val emailAddress: String?,
        val phoneNumber: String?,
        val description: ExtendedDescriptionDto?,
        val photoUrl: String?
)