package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.email.application.ChangeEmailGroupNameApplicationService
import org.springframework.stereotype.Component

@Component
class EmailGroupCommandGateway internal constructor(
    private val changeEmailGroupNameApplicationService: ChangeEmailGroupNameApplicationService
) : CommandGateway {

    fun process(command: EmailGroupCommand.ChangeEmailGroupName) =
        changeEmailGroupNameApplicationService.execute(command)

}
