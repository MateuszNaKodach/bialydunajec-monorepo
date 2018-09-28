package org.bialydunajec.ddd.application.base

interface ApplicationService<CommandType : Command> {
    fun process(command: CommandType)
}