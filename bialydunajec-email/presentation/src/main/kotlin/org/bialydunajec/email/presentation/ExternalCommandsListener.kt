package org.bialydunajec.email.presentation

import org.bialydunajec.ddd.application.base.external.command.ExternalCommandListener
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandSubscriber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.email.application.api.EmailAddressCommand
import org.bialydunajec.email.application.api.EmailAddressCommandGateway
import org.bialydunajec.email.application.api.EmailMessageCommand
import org.bialydunajec.email.application.api.EmailMessageCommandGateway
import org.bialydunajec.email.domain.EmailAddressId
import org.bialydunajec.email.domain.EmailMessageLogId
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner
import org.bialydunajec.email.domain.valueobject.EmailMessage
import org.bialydunajec.email.messages.command.EmailAddressExternalCommand
import org.bialydunajec.email.messages.command.EmailMessageExternalCommand
import org.springframework.stereotype.Component

@Component
internal class ExternalCommandsListener internal constructor(
    private val emailMessageCommandGateway: EmailMessageCommandGateway,
    private val emailAddressCommandGateway: EmailAddressCommandGateway,
    externalCommandSubscriber: ExternalCommandSubscriber
) : ExternalCommandListener {

    init {
        externalCommandSubscriber.subscribePayload(EmailMessageExternalCommand.SendSimpleEmailMessage::class) {
            emailMessageCommandGateway.process(
                EmailMessageCommand.SendEmailCommand(
                    EmailMessage(
                        EmailAddress(it.recipientEmailAddress),
                        it.subject,
                        it.content,
                        EmailMessageLogId(it.trackingCode)
                    )
                )
            )
        }

        externalCommandSubscriber.subscribePayload(EmailAddressExternalCommand.CatalogizeEmailAddress::class) {
            emailAddressCommandGateway.process(
                EmailAddressCommand.CatalogizeEmailAddress(
                    EmailAddress(it.emailAddress),
                    EmailAddressGroup(it.emailGroupName),
                    EmailAddressOwner(
                        FirstName(it.emailOwnerName),
                        LastName(it.emailOwnerLastName)
                    )
                )
            )
        }

        externalCommandSubscriber.subscribePayload(EmailAddressExternalCommand.UpdateEmailAddress::class) {
            emailAddressCommandGateway.process(
                EmailAddressCommand.UpdateEmailAddress(
                    EmailAddressId.from(it.emailAddressId),
                    EmailAddress(it.newEmailAddress)
                )
            )
        }
    }

}

