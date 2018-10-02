package org.bialydunajec.registrations.presentation.rest

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class GlobalExceptionRestController {


    @ExceptionHandler
    fun processDomainRuleViolationException(domainRuleViolationException: DomainRuleViolationException) = domainRuleViolationException.violatedRules
}