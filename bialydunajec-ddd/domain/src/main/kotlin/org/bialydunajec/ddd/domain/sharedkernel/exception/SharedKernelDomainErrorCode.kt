package org.bialydunajec.ddd.domain.sharedkernel.exception

import org.bialydunajec.ddd.domain.base.exception.DomainErrorCode

enum class SharedKernelDomainErrorCode : DomainErrorCode {
    END_DATE_CANNOT_BE_BEFORE_START_DATE,
    MONEY_VALUE_CANNOT_BE_NEGATIVE_NUMBER,
    CURRENCY_MISMATCH,
    PESEL_NUMBER_IS_NOT_VALID;

    override fun getName() = name;
}