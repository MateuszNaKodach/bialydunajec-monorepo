package org.bialydunajec.ddd.infrastructure.base.email

import org.bialydunajec.ddd.application.base.email.EmailMessage
import org.bialydunajec.ddd.application.base.email.EmailMessageSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import javax.mail.internet.InternetAddress

internal class JavaMailSenderAdapter(private val javaMailSender: JavaMailSender) : EmailMessageSender {

    override fun sendEmailMessage(message: EmailMessage) {
        val mailMessage =
                with(message) {
                    SimpleMailMessage()
                            .apply {
                                setTo(*recipients.map { it.toString() }.toTypedArray())
                                setSubject(message.subject)
                                setText(message.content)
                                setFrom(InternetAddress("zapisy@bialydunajec.org","Biały Dunajec - System Zapisów").toString())
                            }
                }
        javaMailSender.send(mailMessage)
    }
}