package org.bialydunajec.ddd.application.base.external.event

interface ExternalEventSubscriber {
    fun <EventType : ExternalEvent<*>> subscribe(eventType: Class<EventType>, consumer: (EventType)->Unit)
}
