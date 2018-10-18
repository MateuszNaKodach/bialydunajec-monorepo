package org.bialydunajec.academicministry.application.dto

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.PersonalTitle

fun PersonalTitle.toDto() =
        PersonalTitleDto(name, prefix, postfix)

fun PersonalTitleDto.toValueObject() =
        PersonalTitle(name, prefix, postfix)