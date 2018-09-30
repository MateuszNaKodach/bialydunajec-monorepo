package org.bialydunajec.ddd.domain.base.validation

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import java.lang.RuntimeException

sealed class ValidationResult {
    class Valid : ValidationResult()
    class Invalid(val violatedRules: Set<DomainRule>) : ValidationResult()

    fun isValid() =
            when (this) {
                is ValidationResult.Valid -> true
                is ValidationResult.Invalid -> false
            }

    fun isInvalid() = !isValid()

    fun doIfInvalid(doIfInvalid: (ValidationResult.Invalid) -> Any) = doIf(invalid = doIfInvalid)

    fun ifInvalidThrowException(exception: ((ValidationResult.Invalid) -> RuntimeException) = { DomainRuleViolationException.of(this as Invalid) }) = doIfInvalid { exception }


    fun doIf(valid: ((ValidationResult.Valid) -> Any)? = null, invalid: ((ValidationResult.Invalid) -> Any)? = null) {
        when (this) {
            is ValidationResult.Valid -> if (valid != null) valid(this)
            is ValidationResult.Invalid -> if (invalid != null) invalid(this)
        }
    }

    class Buffer internal constructor(violatedRules: Collection<DomainRule> = emptySet()) {
        private val violatedRules = violatedRules.toMutableSet()
        fun addViolatedRule(violatedRule: DomainRule) = also { violatedRules.add(violatedRule) }
        fun addViolatedRuleIf(violatedRule: DomainRule, violationCondition: Boolean) =
                apply { violationCondition.takeIf { it }.also { violatedRules.add(violatedRule) } }

        fun addViolatedRuleIfNot(violatedRule: DomainRule, violationCondition: Boolean) =
                apply { violationCondition.takeIf { it.not() }.also { violatedRules.add(violatedRule) } }

        fun addViolatedRules(violatedRules: Collection<DomainRule>) = also { this.violatedRules.addAll(violatedRules) }
        fun toValidationResult() = if (violatedRules.isEmpty()) Valid() else Invalid(violatedRules)
    }

    companion object {
        fun buffer() = Buffer()
    }
}