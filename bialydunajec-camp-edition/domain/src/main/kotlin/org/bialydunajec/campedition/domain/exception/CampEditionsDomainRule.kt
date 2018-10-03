package org.bialydunajec.campedition.domain.exception

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class CampEditionDomainRule : DomainRule {
    CAMP_EDITION_START_DATE_HAS_TO_BE_BEFORE_END_DATE,
    CAMP_EDITION_END_DATE_HAS_TO_BE_AFTER_START_DATE;

    override fun getName() = name

    override fun getDescription() = ""
}