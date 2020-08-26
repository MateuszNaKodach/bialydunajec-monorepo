package org.bialydunajec.ddd.domain.sharedkernel.validation

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRule
import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import java.lang.RuntimeException

sealed class ValidationResult {
    object Valid : ValidationResult()
    class Invalid(val violatedRules: Set<DomainRule>) : ValidationResult()

    fun isValid() =
            when (this) {
                is Valid -> true
                is Invalid -> false
            }

    fun isInvalid() = !isValid()

    fun doIfInvalid(doIfInvalid: (Invalid) -> Any) = doIf(invalid = doIfInvalid)

    fun doIfValid(doIfValid: (Valid) -> Any) = doIf(valid = doIfValid)

    fun ifInvalidThrowException(exception: ((Invalid) -> RuntimeException) = { DomainRuleViolationException.of(this as Invalid) }) = doIfInvalid { throw exception(it) }


    fun doIf(valid: ((Valid) -> Any)? = null, invalid: ((Invalid) -> Any)? = null) {
        when (this) {
            is Valid -> if (valid != null) valid(this)
            is Invalid -> if (invalid != null) invalid(this)
        }
    }

    class Buffer internal constructor(violatedRules: Collection<DomainRule> = emptySet()) {
        private val violatedRules = violatedRules.toMutableSet()
        fun addViolatedRule(violatedRule: DomainRule) = also { violatedRules.add(violatedRule) }

        fun addViolatedRuleIf(violatedRule: DomainRule, violationCondition: Boolean) = also {
            (if (violationCondition) {
                this.violatedRules.add(violatedRule)
            })
        }

        fun addViolatedRuleIfNot(violatedRule: DomainRule, violationCondition: Boolean) =
                addViolatedRuleIf(violatedRule, !violationCondition)

        fun addViolatedRules(violatedRules: Collection<DomainRule>) = also { this.violatedRules.addAll(violatedRules) }
        fun toValidationResult() = if (violatedRules.isEmpty()) Valid else Invalid(violatedRules)
    }

    companion object {
        fun buffer() = Buffer()
    }
}

class CheckDomainRule(private val domainRule: DomainRule, private val domainRuleCondition: Boolean) {
    fun ifViolatedThrowException() = ValidationResult.buffer()
            .addViolatedRuleIfNot(
                    domainRule,
                    domainRuleCondition
            ).toValidationResult()
            .ifInvalidThrowException()
}


object DomainRuleChecker {

    fun check(domainRule: DomainRule, domainRuleCondition: () -> Boolean?): DomainRuleChecker? =
            if (domainRuleCondition()?.not() ?: false) {
                throw DomainRuleViolationException.of(domainRule)
            } else DomainRuleChecker


}

