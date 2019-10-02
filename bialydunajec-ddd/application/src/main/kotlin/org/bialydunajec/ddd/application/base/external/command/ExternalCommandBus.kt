package org.bialydunajec.ddd.application.base.external.command

interface ExternalCommandBus {
    fun send(command: ExternalCommand<*>)
    fun sendAll(commands: Set<ExternalCommand<*>>) {
        commands.forEach(this::send)
    }
}
