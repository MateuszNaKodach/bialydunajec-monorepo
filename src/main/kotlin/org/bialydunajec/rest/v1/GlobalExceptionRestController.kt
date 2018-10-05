package org.bialydunajec.rest.v1

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLException

@RestControllerAdvice
internal class GlobalExceptionRestController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun processDomainRuleViolationException(domainRuleViolationException: DomainRuleViolationException) = domainRuleViolationException

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun processJdbcSQLException(sqlException: SQLException) = "błąd bazy danych"
}