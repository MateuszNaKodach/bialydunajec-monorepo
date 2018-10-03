package org.bialydunajec.authorization.server.security

internal data class OAuth2UserSnapshot(
        val emailAddress: String,
        val username: String,
        val password: String,
        val enabled: Boolean
)