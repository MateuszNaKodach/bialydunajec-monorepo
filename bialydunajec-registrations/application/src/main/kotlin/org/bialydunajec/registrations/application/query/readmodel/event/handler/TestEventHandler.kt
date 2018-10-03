package org.bialydunajec.registrations.application.query.readmodel.event.handler

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class TestEventHandler {

    @EventListener
    fun handle(domainEvent: DomainEvent<*>) {
        println("Event arrived ${domainEvent.javaClass.name}")
    }

    /*
    @EventListener
    fun handle(domainEvent: CampRegistrationsEditionEvent.CampRegistrationsStarted) {
        println("Event arrived $domainEvent")
    }
    */
}