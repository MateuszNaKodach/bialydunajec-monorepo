package org.bialydunajec.academicministry.application.dto

import org.bialydunajec.ddd.application.base.dto.ExtendedDescriptionDto

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