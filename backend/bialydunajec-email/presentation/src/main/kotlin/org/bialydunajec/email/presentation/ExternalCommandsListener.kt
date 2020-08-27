package org.bialydunajec.email.presentation

import org.bialydunajec.ddd.application.base.external.command.ExternalCommandListener
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandSubscriber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.application.api.EmailCommand
import org.bialydunajec.email.application.api.EmailCommandGateway
import org.bialydunajec.email.application.api.EmailMessageCommand
import org.bialydunajec.email.application.api.EmailMessageCommandGateway
import org.bialydunajec.email.domain.EmailGroupId
import org.bialydunajec.email.domain.EmailMessageLogId
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import org.bialydunajec.email.domain.valueobject.EmailMessage
import org.bialydunajec.email.messages.command.EmailExternalCommand
import org.bialydunajec.email.messages.command.EmailMessageExternalCommand
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
internal class ExternalCommandsListener internal constructor(
        private val emailMessageCommandGateway: EmailMessageCommandGateway,
        private val emailCommandGateway: EmailCommandGateway,
        private val externalCommandSubscriber: ExternalCommandSubscriber
) : ExternalCommandListener {

    init {

        mapToEmailMessageCommand<EmailMessageExternalCommand.SendSimpleEmailMessage> {
            EmailMessageCommand.SendEmailCommand(
                    EmailMessage(
                            EmailAddress(recipientEmailAddress),
                            subject,
                            content,
                            EmailMessageLogId(trackingCode)
                    )
            )
        }

        mapToEmailCommand<EmailExternalCommand.CatalogizeEmail> {
            EmailCommand.CatalogizeEmail(
                    EmailAddress(emailAddress),
                    emailGroupId?.let { EmailGroupId(it) },
                    EmailAddressOwner(
                            FirstName(emailOwnerName),
                            LastName(emailOwnerLastName)
                    )
            )
        }

        mapToEmailCommand<EmailExternalCommand.ChangeEmailAddress> {
            EmailCommand.ChangeEmailAddress(
                    EmailAddress(oldEmailAddress),
                    emailGroupId?.let { EmailGroupId(it) },
                    EmailAddress(newEmailAddress)
            )
        }

        mapToEmailCommand<EmailExternalCommand.CorrectEmailOwner> {
            EmailCommand.CorrectEmailOwner(
                    EmailAddress(emailAddress),
                    emailGroupId?.let { EmailGroupId(it) },
                    EmailAddressOwner(
                            FirstName(emailOwnerName),
                            LastName(emailOwnerLastName)
                    )
            )
        }

    }

    private inline fun <reified PayloadType : EmailExternalCommand> mapToEmailCommand(crossinline to: PayloadType.() -> EmailCommand) {
        externalCommandSubscriber {
            subscribePayload(PayloadType::class) { emailCommandGateway.process(to(this)) }
        }
    }

    private inline fun <reified PayloadType : EmailMessageExternalCommand> mapToEmailMessageCommand(crossinline to: PayloadType.() -> EmailMessageCommand) {
        externalCommandSubscriber {
            subscribePayload(PayloadType::class) { emailMessageCommandGateway.process(to(this)) }
        }
    }

}

