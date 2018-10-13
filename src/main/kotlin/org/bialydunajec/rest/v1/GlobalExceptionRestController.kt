package org.bialydunajec.rest.v1

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.presentation.rest.v1.RestErrorDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class GlobalExceptionRestController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun processDomainRuleViolationException(domainRuleViolationException: DomainRuleViolationException): RestErrorDto = domainRuleViolationException.toDto()
}

fun DomainRuleViolationException.toDto() = RestErrorDto(
        this.violatedRules.map { it.getDescription() ?: it.getRuleName() }.toSet()
)