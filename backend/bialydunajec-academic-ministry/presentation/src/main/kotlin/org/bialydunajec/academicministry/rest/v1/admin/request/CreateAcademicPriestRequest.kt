package org.bialydunajec.academicministry.rest.v1.admin.request

import org.bialydunajec.academicministry.application.dto.PersonalTitleDto
import org.bialydunajec.ddd.base.dto.ExtendedDescriptionDto

internal class CreateAcademicPriestRequest(
        val firstName: String,
        val lastName: String,
        val personalTitle: PersonalTitleDto?,
        val emailAddress: String?,
        val phoneNumber: String?,
        val description: ExtendedDescriptionDto?,
        val photoUrl: String?
)