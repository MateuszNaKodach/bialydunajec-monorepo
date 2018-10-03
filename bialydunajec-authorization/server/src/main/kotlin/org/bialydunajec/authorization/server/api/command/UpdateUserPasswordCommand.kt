package org.bialydunajec.authorization.server.api.command

import javax.validation.constraints.NotBlank

data class UpdateUserPasswordCommand(
        @NotBlank
        val userId: String,
        @NotBlank
        val oldPassword: String,
        @NotBlank
        val newPassword: String
)