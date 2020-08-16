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

@Component
internal class ExternalCommandsListener internal constructor(
    private val emailMessageCommandGateway: EmailMessageCommandGateway,
    private val emailCommandGateway: EmailCommandGateway,
    externalCommandSubscriber: ExternalCommandSubscriber
) : ExternalCommandListener {

    init {
        externalCommandSubscriber.subscribePayload(EmailMessageExternalCommand.SendSimpleEmailMessage::class) {
            emailMessageCommandGateway.process(
                EmailMessageCommand.SendEmailCommand(
                    EmailMessage(
                        EmailAddress(recipientEmailAddress),
                        subject,
                        content,
                        EmailMessageLogId(trackingCode)
                    )
                )
            )
        }

        externalCommandSubscriber.subscribePayload(EmailExternalCommand.CatalogizeEmail::class) {
            emailCommandGateway.process(
                EmailCommand.CatalogizeEmail(
                    EmailAddress(emailAddress),
                    emailGroupId?.let { EmailGroupId(it) },
                    EmailAddressOwner(
                        FirstName(emailOwnerName),
                        LastName(emailOwnerLastName)
                    )
                )
            )
        }

        externalCommandSubscriber.subscribePayload(EmailExternalCommand.ChangeEmailAddress::class) {
            emailCommandGateway.process(
                EmailCommand.ChangeEmailAddress(
                    EmailAddress(oldEmailAddress),
                    emailGroupId?.let { EmailGroupId(it) },
                    EmailAddress(newEmailAddress)
                )
            )
        }

        externalCommandSubscriber.subscribePayload(EmailExternalCommand.CorrectEmailOwner::class) {
            emailCommandGateway.process(
                EmailCommand.CorrectEmailOwner(
                    EmailAddress(emailAddress),
                    emailGroupId?.let { EmailGroupId(it) },
                    EmailAddressOwner(
                        FirstName(emailOwnerName),
                        LastName(emailOwnerLastName)
                    )
                )
            )
        }

    }

}

