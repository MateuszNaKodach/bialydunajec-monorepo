package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.EmailMessageLogId
import org.bialydunajec.email.domain.valueobject.EmailMessage

sealed class EmailMessageCommand : Command {

    class SendEmailCommand(val emailMessage: EmailMessage) : EmailMessageCommand()
    class ResendEmailCommand(val emailMessageLogId: EmailMessageLogId) : EmailMessageCommand()
    class ForwardEmailCommand(val emailMessageLogId: EmailMessageLogId, val recipients: Set<EmailAddress>) : EmailMessageCommand()

}
