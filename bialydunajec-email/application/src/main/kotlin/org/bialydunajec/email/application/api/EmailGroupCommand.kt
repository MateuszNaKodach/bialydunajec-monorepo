package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.email.domain.EmailGroupId
import org.bialydunajec.email.domain.valueobject.EmailGroupName

sealed class EmailGroupCommand : Command {

    class ChangeEmailGroupName(
        val emailGroupId: EmailGroupId,
        val newName: EmailGroupName
    ) : Command

}
