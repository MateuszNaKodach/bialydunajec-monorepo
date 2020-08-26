package org.bialydunajec.academicministry.application.dto

import org.bialydunajec.academicministry.domain.valueobject.AcademicPriestSnapshot
import org.bialydunajec.ddd.application.base.dto.toDto
import org.bialydunajec.ddd.domain.sharedkernel.extensions.toStringOrNull
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.PersonalTitle

fun PersonalTitle.toDto() =
        PersonalTitleDto(name.toStringOrNull(), prefix.toStringOrNull(), postfix.toStringOrNull())

fun PersonalTitleDto.toValueObject() =
        PersonalTitle(name.toStringOrNull(), prefix.toStringOrNull(), postfix.toStringOrNull())

fun AcademicPriestSnapshot.toDto() =
        AcademicPriestDto(
                academicPriestId.toString(),
                firstName.toString(),
                lastName.toString(),
                personalTitle?.toDto(),
                emailAddress.toStringOrNull(),
                phoneNumber.toStringOrNull(),
                description?.toDto(),
                photoUrl.toStringOrNull()
        )
