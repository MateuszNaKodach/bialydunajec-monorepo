package org.bialydunajec.users.application.command.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.users.application.command.ChangePasswordApplicationService
import org.springframework.stereotype.Component

@Component
class CurrentUserAdminCommandGateway internal constructor(
        private val changePasswordApplicationService: ChangePasswordApplicationService
) : CommandGateway {

    fun process(command: CurrentUserAdminCommand.ChangePassword) =
            changePasswordApplicationService.execute(command)
}