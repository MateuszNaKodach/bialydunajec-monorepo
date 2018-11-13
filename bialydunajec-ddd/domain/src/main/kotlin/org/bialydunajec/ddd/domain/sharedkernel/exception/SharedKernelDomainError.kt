package org.bialydunajec.ddd.domain.sharedkernel.exception

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class SharedKernelDomainError : DomainRule {
    END_DATE_CANNOT_BE_BEFORE_START_DATE,
    MONEY_VALUE_CANNOT_BE_NEGATIVE_NUMBER,
    CALCULATION_CURRENCIES_MUST_BE_THE_SAME,
    PESEL_NUMBER_MUST_BE_VALID,
    BASE_64_IS_CORRUPTED;

    override fun getRuleName() = name
    override fun getDescription() = null
}