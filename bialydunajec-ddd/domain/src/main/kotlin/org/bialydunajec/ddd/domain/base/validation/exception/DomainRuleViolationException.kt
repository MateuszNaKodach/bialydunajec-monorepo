package org.bialydunajec.ddd.domain.base.validation.exception

import org.bialydunajec.ddd.domain.base.validation.ValidationResult

open class DomainRuleViolationException private constructor(
        val violatedRules: Set<DomainRule>,
        cause: Throwable? = null
) : RuntimeException(violatedRules.joinToString(", ") { it.getDescription() }, cause) {

    private constructor(violatedRule: DomainRule, cause: Throwable? = null) : this(setOf(violatedRule), cause)

    fun containsViolatedRule(violatedRule: DomainRule) = violatedRules.contains(violatedRule)

    companion object {
        fun of(invalid: ValidationResult.Invalid, cause: Throwable? = null) = DomainRuleViolationException(violatedRules = invalid.violatedRules, cause = cause)

        fun of(violatedRule: DomainRule, cause: Throwable? = null) = DomainRuleViolationException(violatedRule = violatedRule, cause = cause)

        fun throwOf(violatedRule: DomainRule, cause: Throwable? = null): Nothing = throw of(violatedRule, cause)

        fun of(violatedRules: Set<DomainRule>, cause: Throwable? = null) = DomainRuleViolationException(violatedRules = violatedRules, cause = cause)

        fun throwOf(violatedRules: Set<DomainRule>, cause: Throwable? = null): Nothing = throw of(violatedRules, cause)

        fun throwOf(invalid: ValidationResult.Invalid, cause: Throwable? = null): Nothing = throw of(invalid, cause)
    }
}