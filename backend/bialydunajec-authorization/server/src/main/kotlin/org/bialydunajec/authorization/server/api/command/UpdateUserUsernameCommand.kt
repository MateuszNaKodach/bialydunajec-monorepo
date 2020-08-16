package org.bialydunajec.authorization.server.api.command

import javax.validation.constraints.NotBlank

data class UpdateUserUsernameCommand(
        @NotBlank
        val userId: String,
        @NotBlank
        val username: String
)