package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.ddd.application.base.command.CommandProcessor
import org.bialydunajec.email.application.ForwardEmailMessageApplicationService
import org.bialydunajec.email.application.ResendEmailMessageApplicationService
import org.bialydunajec.email.application.SendEmailMessageApplicationService
import org.springframework.stereotype.Component

@Component
class EmailMessageCommandGateway internal constructor(
        private val sendEmailMessageApplicationService: SendEmailMessageApplicationService,
        private val resendEmailMessageApplicationService: ResendEmailMessageApplicationService,
        private val forwardEmailMessageApplicationService: ForwardEmailMessageApplicationService
) : CommandGateway, CommandProcessor<EmailMessageCommand> {

    override fun process(command: EmailMessageCommand): Any? = when (command) {
        is EmailMessageCommand.SendEmailCommand -> process(command)
        is EmailMessageCommand.ResendEmailCommand -> process(command)
        is EmailMessageCommand.ForwardEmailCommand -> process(command)
    }

    fun process(command: EmailMessageCommand.SendEmailCommand) =
            sendEmailMessageApplicationService.execute(command)

    fun process(command: EmailMessageCommand.ResendEmailCommand) =
            resendEmailMessageApplicationService.execute(command)

    fun process(command: EmailMessageCommand.ForwardEmailCommand) =
            forwardEmailMessageApplicationService.execute(command)
}
