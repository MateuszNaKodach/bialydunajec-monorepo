package org.bialydunajec.registrations.domain.exception

import org.bialydunajec.ddd.domain.base.exception.DomainErrorCode

enum class CampersRegisterDomainErrorCode : DomainErrorCode {
    ACADEMIC_MINISTRY_NOT_FOUND,
    COTTAGE_NOT_FOUND,
    CAMP_REGISTRATIONS_NOT_FOUND,
    CAMP_EDITION_NOT_FOUND,
    CAMP_EDITION_HAS_NOT_IN_PROGRESS_REGISTRATIONS,
    IN_PROGRESS_CAMP_REGISTRATIONS_NOT_FOUND,
    NO_DEFINED_ACADEMIC_MINISTRY_FOR_COTTAGE,
    CHECK_IN_TIME_CAN_NOT_BE_SPECIFIED_WITHOUT_CHECK_IN_DATE,
    CHECK_OUT_TIME_CAN_NOT_BE_SPECIFIED_WITHOUT_CHECK_OUT_DATE;

    override fun getName() = name;
}