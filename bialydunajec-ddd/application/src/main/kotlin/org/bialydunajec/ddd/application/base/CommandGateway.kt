package org.bialydunajec.ddd.application.base

interface CommandGateway<CommandType : Command> {

    fun process(command: CommandType)

}