package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage
import org.bialydunajec.email.application.EmailMessageSender
import org.bialydunajec.email.application.EmailSendingResult
import org.slf4j.LoggerFactory

internal class LogFileMailSenderAdapter : EmailMessageSender {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun sendEmailMessage(message: SimpleEmailMessage): EmailSendingResult =
            when ((0..10).random()) {
                4 -> {
                    log.info("Email message wasn't sent. It's random error for development environment.")
                    EmailSendingResult.Failure("Random error from log file.")
                }
                else -> {
                    log.info("Email message sent success to log file.")
                    EmailSendingResult.Success
                }
            }

}