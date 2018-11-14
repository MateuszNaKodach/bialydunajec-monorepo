package org.bialydunajec.email.presentation

import org.bialydunajec.ddd.application.base.external.command.ExternalCommand
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandListener
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.application.api.EmailCommand
import org.bialydunajec.email.application.api.EmailCommandGateway
import org.bialydunajec.email.domain.EmailMessageLogId
import org.bialydunajec.email.domain.valueobject.EmailMessage
import org.bialydunajec.email.messages.command.EmailMessageExternalCommand
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
internal class ExternalCommandsListener internal constructor(
        private val commandGateway: EmailCommandGateway
) : ExternalCommandListener {

    @Async
    @EventListener
    override fun handleExternalCommand(externalCommand: ExternalCommand<*>) {
        val payload = externalCommand.payload
        when (payload) {
            is EmailMessageExternalCommand.SendSimpleEmailMessage -> {
                commandGateway.process(
                        EmailCommand.SendEmailCommand(
                                EmailMessage(
                                        EmailAddress(payload.recipientEmailAddress),
                                        payload.subject,
                                        payload.content,
                                        EmailMessageLogId(payload.trackingCode)
                                )
                        )
                )
            }
        }
    }
}

