package org.bialydunajec.ddd.domain.base.exception

open class BusinessRuleViolationException(
        val errorCode: DomainErrorCode,
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message ?: errorCode.getName(), cause) {

    companion object {
        fun of(errorCode: DomainErrorCode, cause: Throwable? = null) = BusinessRuleViolationException(errorCode = errorCode, cause = cause)

        fun of(errorCode: DomainErrorCode, message: String? = null, cause: Throwable? = null) = BusinessRuleViolationException(errorCode = errorCode, message = message, cause = cause)

        fun throwWithErrorCode(errorCode: DomainErrorCode): Unit = throw of(errorCode)
    }
}