package org.bialydunajec.campschedule.domain.exception

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class CampEditionScheduleDomainRule : DomainRule {
    CAMP_DAY_DATE_CAN_NOT_BE_DUPLICATED_IN_ONE_SCHEDULE {
        override fun getDescription(): String? = "Ta sama data nie może być zaplanowana dwukrotnie w czasie jednej Edycji Obozu."
    };

    override fun getRuleName() = name
    override fun getDescription(): String? = null
}
