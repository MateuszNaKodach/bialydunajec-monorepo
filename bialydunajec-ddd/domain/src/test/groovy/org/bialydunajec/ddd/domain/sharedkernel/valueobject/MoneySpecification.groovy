package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import org.bialydunajec.ddd.domain.base.exception.BusinessRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.exception.SharedKernelDomainErrorCode
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import spock.lang.Specification

class MoneySpecification extends Specification {

    def 'money value cannot be negative'() {
        when:
        new Money(negativeNumber, "PLN")

        then:
        BusinessRuleViolationException exception = thrown()
        exception.errorCode == SharedKernelDomainErrorCode.MONEY_VALUE_CANNOT_BE_NEGATIVE_NUMBER

        where:
        negativeNumber || _
        -0.01d         || _
        -1             || _
        -99            || _
        -100           || _
        -999999999     || _
    }

    def 'money value can be zero or positive'() {
        when:
        def money = new Money(moneyValue, "USD")

        then:
        money.value == moneyValue

        where:
        moneyValue   || _
        0            || _
        1            || _
        99.23d       || _
        999          || _
        99999.99d    || _
        999999999.0d || _
        999999999999 || _
    }
}
