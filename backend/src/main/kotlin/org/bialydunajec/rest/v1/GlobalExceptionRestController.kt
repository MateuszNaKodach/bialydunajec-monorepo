package org.bialydunajec.rest.v1

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.ddd.presentation.rest.v1.RestErrorDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class GlobalExceptionRestController {

    protected val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun processDomainRuleViolationException(domainRuleViolationException: DomainRuleViolationException): RestErrorDto {
        log.info("DomainRuleViolationException occurs: {}", domainRuleViolationException)
        return domainRuleViolationException.toDto()
    }
}

fun DomainRuleViolationException.toDto() = RestErrorDto(
        this.violatedRules.map { it.getDescription() ?: it.getRuleName() }.toSet()
)
