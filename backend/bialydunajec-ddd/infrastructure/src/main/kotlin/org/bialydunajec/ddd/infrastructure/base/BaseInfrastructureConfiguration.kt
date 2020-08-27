package org.bialydunajec.ddd.infrastructure.base

import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.infrastructure.base.event.InMemoryEventsStorage
import org.bialydunajec.ddd.infrastructure.base.event.SpringDomainEventBus
import org.bialydunajec.ddd.infrastructure.base.event.storeEventsIn
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BaseInfrastructureConfiguration(private val applicationEventPublisher: ApplicationEventPublisher){

    @Bean
    fun domainEventBus(): DomainEventBus =
            SpringDomainEventBus(applicationEventPublisher)
                    //.storeEventsIn(InMemoryEventsStorage())

}
