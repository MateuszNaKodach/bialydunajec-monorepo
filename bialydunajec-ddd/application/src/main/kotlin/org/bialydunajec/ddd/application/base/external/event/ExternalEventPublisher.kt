package org.bialydunajec.ddd.application.base.external.event

interface ExternalEventPublisher {
    fun send(event: Any) {
        send(ExternalEvent(event))
    }

    fun send(event: ExternalEvent<*>)

    fun sendAll(events: Set<ExternalEvent<*>>) {
        events.forEach(this::send)
    }
}
