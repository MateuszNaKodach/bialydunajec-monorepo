package org.bialydunajec.authorization.server.api.command

import javax.validation.constraints.NotBlank

data class UpdateUserEmailAddressCommand(
        @NotBlank
        val userId: String,
        @NotBlank
        val emailAddress: String
)