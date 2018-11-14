package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.email.application.SendEmailMessageApplicationService
import org.springframework.stereotype.Component

@Component
class EmailCommandGateway internal constructor(
        private val sendEmailMessageApplicationService: SendEmailMessageApplicationService
) : CommandGateway {

    fun process(command: EmailCommand.SendEmailCommand) =
            sendEmailMessageApplicationService.execute(command)

}