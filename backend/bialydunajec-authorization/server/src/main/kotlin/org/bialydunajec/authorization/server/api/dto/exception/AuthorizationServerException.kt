package org.bialydunajec.authorization.server.api.dto.exception

class AuthorizationServerException : RuntimeException {
    val errorCode: AuthorizationErrorCode

    private constructor(errorCode: AuthorizationErrorCode, cause: Throwable) : super(errorCode.name, cause) {
        this.errorCode = errorCode
    }

    private constructor(errorCode: AuthorizationErrorCode, message: String? = errorCode.name, cause: Throwable?) : super(message, cause) {
        this.errorCode = errorCode
    }

    companion object {

        fun of(errorCode: AuthorizationErrorCode, cause: Throwable): AuthorizationServerException {
            return AuthorizationServerException(errorCode, cause)
        }

        fun of(errorCode: AuthorizationErrorCode, message: String? = null, cause: Throwable? = null): AuthorizationServerException {
            return AuthorizationServerException(errorCode, message, cause)
        }
    }
}