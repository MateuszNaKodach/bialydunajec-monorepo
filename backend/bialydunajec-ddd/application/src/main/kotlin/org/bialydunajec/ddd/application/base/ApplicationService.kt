package org.bialydunajec.ddd.application.base

import org.bialydunajec.ddd.application.base.command.Command

interface ApplicationService<CommandType : Command> {
    fun execute(command: CommandType): Any
}

inline fun <reified CommandType : Command> ApplicationService<CommandType>.handle(command: Command) = command is CommandType
