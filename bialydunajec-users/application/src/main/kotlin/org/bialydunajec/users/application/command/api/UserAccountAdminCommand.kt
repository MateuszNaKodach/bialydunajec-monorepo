package org.bialydunajec.users.application.command.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.user.Password
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.user.Username
import org.bialydunajec.users.domain.UserAccountId

sealed class UserAccountAdminCommand : Command {
    class CreateUserAccount(
            val emailAddress: EmailAddress,
            val password: Password,
            val username: Username? = null,
            val firstName: FirstName? = null,
            val lastName: LastName? = null
    ) : UserAccountAdminCommand()
}