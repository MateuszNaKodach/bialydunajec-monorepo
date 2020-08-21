package org.bialydunajec.authorization.server.api.dto.exception

enum class AuthorizationErrorCode {
    USER_CREDENTIALS_NOT_FOUND,
    CLIENT_CREDENTIALS_NOT_FOUND,
    INVALID_ACCESS_TOKEN,
    INVALID_USER_PASSWORD,
    NO_LOGGED_USER;
}