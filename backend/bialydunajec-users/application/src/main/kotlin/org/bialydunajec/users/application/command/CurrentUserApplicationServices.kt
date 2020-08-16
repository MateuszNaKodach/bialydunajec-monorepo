package org.bialydunajec.users.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.users.application.command.api.CurrentUserAdminCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class ChangePasswordApplicationService(
) : ApplicationService<CurrentUserAdminCommand.ChangePassword> {

    override fun execute(command: CurrentUserAdminCommand.ChangePassword) {

    }

}