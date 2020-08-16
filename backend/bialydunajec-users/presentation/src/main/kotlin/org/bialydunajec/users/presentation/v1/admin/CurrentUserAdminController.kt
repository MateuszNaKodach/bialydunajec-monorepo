package org.bialydunajec.users.presentation.v1.admin

import org.bialydunajec.users.application.command.api.CurrentUserAdminCommandGateway
import org.bialydunajec.users.application.command.api.CurrentUserAdminCommand
import org.bialydunajec.users.presentation.v1.admin.request.ChangePasswordRequest
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest-api/v1/admin/me")
internal class CurrentUserAdminController(
        private val currentUserAdminCommandGateway: CurrentUserAdminCommandGateway
) {

    /*
    @PutMapping("/password")
    fun changePassword(@RequestBody request: ChangePasswordRequest) =
            currentUserAdminCommandGateway.execute(
                    CurrentUserAdminCommand.ChangePassword()
            )

           */
}