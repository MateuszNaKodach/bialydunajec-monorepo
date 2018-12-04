package org.bialydunajec.email.infrastructure

import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage
import org.bialydunajec.email.application.EmailMessageSender
import org.bialydunajec.email.application.EmailSendingResult
import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import javax.mail.internet.InternetAddress

internal class JavaMailSenderAdapter(private val javaMailSender: JavaMailSender) : EmailMessageSender {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun sendEmailMessage(message: SimpleEmailMessage): EmailSendingResult =
            with(message) {
                SimpleMailMessage()
                        .apply {
                            setTo(*recipients.map { it.toString() }.toTypedArray())
                            setSubject(message.subject)
                            setText(message.content)
                            setFrom(InternetAddress("zapisy@bialydunajec.org", "Biały Dunajec - System Zapisów").toString())
                        }
            }.let {
                try {
                    javaMailSender.send(it)
                    log.info("Email message sent {}", message)
                    EmailSendingResult.Success()
                } catch (e: Exception) {
                    log.error("Email message wasn't sent {}", message)
                    EmailSendingResult.Failure(e.localizedMessage)
                }
            }


}