package org.bialydunajec.academicministry.domain.exception

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class AcademicMinistryDomainRule : DomainRule {
    ACADEMIC_MINISTRY_TO_UPDATE_MUST_EXISTS;

    override fun getRuleName() = name
    override fun getDescription() = null
}