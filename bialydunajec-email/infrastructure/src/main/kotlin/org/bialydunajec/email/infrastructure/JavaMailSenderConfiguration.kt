package org.bialydunajec.email.infrastructure

import org.bialydunajec.email.application.EmailMessageSender
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender

@Configuration
internal class JavaMailSenderConfiguration {

    @Primary
    @Profile("mail_send")
    @Bean
    fun javaMailSenderAdapter(javaMailSender: JavaMailSender): EmailMessageSender {
        return JavaMailSenderAdapter(javaMailSender)
    }

    @Profile("mail_log")
    @Bean
    fun logFileMailSenderAdapter(): EmailMessageSender {
        return LogFileMailSenderAdapter()
    }
}