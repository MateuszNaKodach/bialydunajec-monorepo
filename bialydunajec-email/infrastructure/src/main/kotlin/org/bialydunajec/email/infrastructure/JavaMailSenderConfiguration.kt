package org.bialydunajec.email.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender

@Configuration
internal class JavaMailSenderConfiguration {

    @Bean
    fun javaMailSenderAdapter(javaMailSender: JavaMailSender): JavaMailSenderAdapter {
        return JavaMailSenderAdapter(javaMailSender)
    }
}