package org.bialydunajec.academicministry.domain.exception

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRule

enum class AcademicMinistryDomainRule : DomainRule {
    ACADEMIC_MINISTRY_TO_UPDATE_MUST_EXISTS,
    ACADEMIC_MINISTRY_FOR_PRIEST_MUST_EXISTS;

    override fun getRuleName() = name
    override fun getDescription() = null
}
