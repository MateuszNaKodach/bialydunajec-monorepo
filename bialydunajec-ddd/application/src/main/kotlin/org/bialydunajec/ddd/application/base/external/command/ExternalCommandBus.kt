package org.bialydunajec.ddd.application.base.external.command

interface ExternalCommandBus {
    fun send(command: ExternalCommand<*>)

    fun sendPayload(payload: Any) {
        send(ExternalCommand(payload))
    }

    fun sendAll(commands: Collection<ExternalCommand<*>>) {
        commands.forEach(this::send)
    }

    fun sendAllPayloads(payloads: Collection<Any>) {
        payloads.forEach(this::sendPayload)
    }
}
