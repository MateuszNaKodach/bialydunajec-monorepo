package org.bialydunajec.authorization.server.api.command

import javax.validation.constraints.NotBlank

data class CreateUserCredentialsCommand(
        @NotBlank
        val emailAddress: String,
        @NotBlank
        val username: String,
        @NotBlank
        val password: String
)