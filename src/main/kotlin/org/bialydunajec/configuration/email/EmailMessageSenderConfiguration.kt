package org.bialydunajec.configuration.email

import org.bialydunajec.ddd.application.base.email.EmailMessageSenderPort
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandBus
import org.bialydunajec.ddd.infrastructure.base.email.BialyDunajecMailSenderAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class EmailMessageSenderConfiguration {

    @Bean
    fun emailMessageSenderPort(externalCommandBus: ExternalCommandBus): EmailMessageSenderPort = BialyDunajecMailSenderAdapter(externalCommandBus)
}