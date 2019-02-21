package org.bialydunajec.campschedule.domain.exception

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class CampEditionScheduleDomainRule : DomainRule {
    CAMP_DAY_DATE_CAN_NOT_BE_DUPLICATED_IN_ONE_SCHEDULE {
        override fun getDescription(): String? = "Ta sama data nie może być zaplanowana dwukrotnie w czasie jednej Edycji Obozu."
    },
    CAMP_DAY_CAN_NOT_BE_SCHEDULE_BEFORE_CAMP_EDITION_START {
        override fun getDescription(): String? = "Dzień Obozu musi zostać zaplanowany na czas jego trwania."
    },
    CAMP_DAY_CAN_NOT_BE_SCHEDULE_AFTER_CAMP_EDITION_END {
        override fun getDescription(): String? = "Dzień Obozu musi zostać zaplanowany na czas jego trwania."
    },
    DAY_ACTIVITY_START_TIME_CAN_NOT_BE_AFTER_END_TIME {
        override fun getDescription(): String? = "Czas rozpoczęcia punktu dnia nie może być późniejszy niż czas zakończenia."
    },
    DAY_ACTIVITY_END_TIME_CAN_NOT_BE_BEFORE_START_TIME {
        override fun getDescription(): String? = "Czas zakończenia punktu dnia nie może być wcześniejszy niż czas rozpoczęcia."
    },
    DAY_TO_CANCEL_MUST_EXISTS {
        override fun getDescription(): String? = "Dzień do odwołania musi istnieć."
    };

    override fun getRuleName() = name
    override fun getDescription(): String? = null
}
