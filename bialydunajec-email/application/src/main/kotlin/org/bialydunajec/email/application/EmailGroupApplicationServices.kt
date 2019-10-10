package org.bialydunajec.email.application

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.email.application.api.EmailGroupCommand
import org.bialydunajec.email.domain.EmailGroup
import org.bialydunajec.email.domain.EmailGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class ChangeEmailGroupNameApplicationService(
    private val emailGroupRepository: EmailGroupRepository
) : ApplicationService<EmailGroupCommand.ChangeEmailGroupName> {

    override fun execute(command: EmailGroupCommand.ChangeEmailGroupName) {
        val emailGroup = emailGroupRepository.findById(command.emailGroupId)
            ?: EmailGroup(command.emailGroupId)
        emailGroup.changeName(command.newName)
        emailGroupRepository.save(emailGroup)
    }

}
