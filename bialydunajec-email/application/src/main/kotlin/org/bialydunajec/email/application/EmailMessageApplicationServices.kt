package org.bialydunajec.email.application

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage
import org.bialydunajec.ddd.application.base.time.Clock
import org.bialydunajec.email.application.api.EmailCommand
import org.bialydunajec.email.domain.EmailMessageLog
import org.bialydunajec.email.domain.EmailMessageLogId
import org.bialydunajec.email.domain.EmailMessageLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class SendEmailMessageApplicationService(
        private val emailMessageSender: EmailMessageSender,
        private val emailMessageLogRepository: EmailMessageLogRepository,
        private val clock: Clock
) : ApplicationService<EmailCommand.SendEmailCommand> {

    override fun execute(command: EmailCommand.SendEmailCommand) {
        val emailMessage = with(command.emailMessage) {
            SimpleEmailMessage(
                    recipient,
                    subject,
                    content
            )
        }
        val emailSendingResult = emailMessageSender.sendEmailMessage(emailMessage)

        val emailMessageLog = with(command.emailMessage) {
            EmailMessageLog(
                    recipient,
                    subject,
                    content,
                    emailMessageLogId ?: EmailMessageLogId()
            )
        }

        when (emailSendingResult) {
            is EmailSendingResult.Success -> {
                emailMessageLog.markAsSent(clock.currentDateTime())
            }
            is EmailSendingResult.Failure -> {
                emailMessageLog.logSendingFailure(emailSendingResult.errorMessage)
            }
        }

        emailMessageLogRepository.save(emailMessageLog)
    }
}