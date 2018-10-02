package org.bialydunajec.ddd.application.base.command

interface CommandBus {
    fun send(command: Command)
    fun sendAll(commands: Set<Command>) {
        commands.forEach(this::send)
    }
}