package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.email.domain.EmailMessageLogId
import org.bialydunajec.email.domain.valueobject.EmailMessage

sealed class EmailCommand : Command {

    class SendEmailCommand(val emailMessage: EmailMessage) : Command
    class ResendEmailCommand(val emailMessageLogId: EmailMessageLogId) : Command

}