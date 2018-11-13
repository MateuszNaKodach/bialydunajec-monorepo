package org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.bialydunajec.ddd.domain.extensions.isNegative
import org.bialydunajec.ddd.domain.extensions.isZero
import org.bialydunajec.ddd.domain.sharedkernel.exception.SharedKernelDomainError
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.persistence.Embeddable
import javax.validation.constraints.PositiveOrZero

private const val MONEY_DEFAULT_CURRENCY_CODE = "PLN"

@Embeddable
class Money private constructor(
        @PositiveOrZero
        private val value: BigDecimal,
        @org.hibernate.validator.constraints.Currency
        private val currency: Currency
) : ValueObject {

    constructor(value: Int, currencyCode: String = MONEY_DEFAULT_CURRENCY_CODE) : this(BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN), Currency.getInstance(currencyCode))

    constructor(value: Double, currencyCode: String = MONEY_DEFAULT_CURRENCY_CODE) : this(BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN), Currency.getInstance(currencyCode))

    constructor(value: BigDecimal, currencyCode: String = MONEY_DEFAULT_CURRENCY_CODE) : this(value.setScale(2, RoundingMode.HALF_EVEN), Currency.getInstance(currencyCode))

    init {
        if (value.isNegative()) {
            throw DomainRuleViolationException.of(SharedKernelDomainError.MONEY_VALUE_CANNOT_BE_NEGATIVE_NUMBER)
        }
    }

    fun multiplyBy(multiplier: Double): Money {
        return multiplyBy(BigDecimal(multiplier))
    }

    fun multiplyBy(multiplier: BigDecimal): Money {
        return Money(value.multiply(multiplier), currency)
    }

    fun add(money: Money): Money {
        if (!compatibleCurrency(money)) {
            throw DomainRuleViolationException.of(SharedKernelDomainError.CALCULATION_CURRENCIES_MUST_BE_THE_SAME)
        }
        return Money(value.add(money.value), determineCurrencyCode(money))
    }

    fun subtract(money: Money): Money {
        if (!compatibleCurrency(money)) {
            throw DomainRuleViolationException.of(SharedKernelDomainError.CALCULATION_CURRENCIES_MUST_BE_THE_SAME)
        }
        return Money(value.subtract(money.value), determineCurrencyCode(money))
    }

    /**
     * Currency is compatible if the same or either money object has zero value.
     */
    private fun compatibleCurrency(money: Money) =
            value.isZero() || money.value.isZero() || currency.currencyCode == money.currency.currencyCode

    /**
     * @return currency from this object or otherCurrencyCode. Preferred is the
     * one that comes from Money that has non-zero value.
     */
    private fun determineCurrencyCode(otherMoney: Money): Currency {
        val resultingCurrencyCode = if (value.isZero()) otherMoney.currency.currencyCode else currency.currencyCode
        return Currency.getInstance(resultingCurrencyCode)
    }

    fun getCurrencyCode() = currency.currencyCode

    fun getCurrency() = currency

    fun getValue() = value

    fun greaterThan(other: Money) = value > other.value

    fun greaterOrEquals(other: Money) = value >= other.value

    fun lessThan(other: Money) = value < other.value

    fun lessOrEquals(other: Money) = value <= other.value

    companion object {
        fun zero(currencyCode: String = MONEY_DEFAULT_CURRENCY_CODE) = Money(0.0, currencyCode)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        if (value != other.value) return false
        if (currency != other.currency) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + currency.hashCode()
        return result
    }


}