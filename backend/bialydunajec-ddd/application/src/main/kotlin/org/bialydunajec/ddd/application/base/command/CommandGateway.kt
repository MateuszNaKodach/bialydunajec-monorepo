package org.bialydunajec.ddd.application.base.command

interface CommandGateway

interface CommandProcessor<CommandType: Command>{
    fun process(command: CommandType): Any?
}
