package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.email.application.ForwardEmailMessageApplicationService
import org.bialydunajec.email.application.ResendEmailMessageApplicationService
import org.bialydunajec.email.application.SendEmailMessageApplicationService
import org.springframework.stereotype.Component

@Component
class EmailCommandGateway internal constructor(
        private val sendEmailMessageApplicationService: SendEmailMessageApplicationService,
        private val resendEmailMessageApplicationService: ResendEmailMessageApplicationService,
        private val forwardEmailMessageApplicationService: ForwardEmailMessageApplicationService
) : CommandGateway {

    fun process(command: EmailCommand.SendEmailCommand) =
            sendEmailMessageApplicationService.execute(command)

    fun process(command: EmailCommand.ResendEmailCommand) =
            resendEmailMessageApplicationService.execute(command)

    fun process(command: EmailCommand.ForwardEmailCommand) =
            forwardEmailMessageApplicationService.execute(command)
}