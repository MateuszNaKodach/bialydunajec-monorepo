package org.bialydunajec.ddd.application.base.external.command

interface ExternalCommandBus {
    fun send(event: ExternalCommand<*>)
    fun sendAll(event: Set<ExternalCommand<*>>) {
        event.forEach(this::send)
    }
}