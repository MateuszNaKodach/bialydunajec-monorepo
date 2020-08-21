package org.bialydunajec.authorization.server.api.command

import javax.validation.constraints.NotBlank

data class SendResetUserPasswordEmailMessageCommand(
        @NotBlank
        val userId: String
)