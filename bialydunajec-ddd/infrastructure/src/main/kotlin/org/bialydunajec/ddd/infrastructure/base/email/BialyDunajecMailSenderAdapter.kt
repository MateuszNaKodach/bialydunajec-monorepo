package org.bialydunajec.ddd.infrastructure.base.email

import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage
import org.bialydunajec.ddd.application.base.email.EmailMessageSenderPort
import org.bialydunajec.ddd.application.base.external.command.ExternalCommand
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandBus
import org.bialydunajec.email.messages.command.EmailMessageExternalCommand

class BialyDunajecMailSenderAdapter(private val externalCommandBus: ExternalCommandBus) : EmailMessageSenderPort {

    override fun sendEmailMessage(message: SimpleEmailMessage) {
        with(message) {
            recipients.forEach { recipient ->
                externalCommandBus.send(
                        ExternalCommand(
                                EmailMessageExternalCommand.SendSimpleEmailMessage(
                                        recipient.toString(),
                                        message.subject,
                                        message.content,
                                        message.trackingCode
                                )
                        )
                )
            }
        }
    }
}