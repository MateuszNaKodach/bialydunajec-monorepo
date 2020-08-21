package org.bialydunajec.users.application.command.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.user.Password
import org.bialydunajec.users.domain.UserAccountId

sealed class CurrentUserAdminCommand(private val userAccountId: UserAccountId) : Command {
    class ChangePassword(
            userAccountId: UserAccountId,
            val oldPassword: Password,
            val newPassword: Password,
            val newPasswordRepeated: Password
    ) : CurrentUserAdminCommand(userAccountId)
}