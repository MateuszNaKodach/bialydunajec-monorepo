package org.bialydunajec.email.application

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.email.application.api.EmailAddressCommand
import org.bialydunajec.email.domain.EmailAddressRepository
import org.bialydunajec.email.domain.EmailGroupRepository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CatalogizeEmailAddressApplicationService(
        private val emailGroupRepository: EmailGroupRepository,
        private val emailAddressRepository: EmailAddressRepository
) : ApplicationService<EmailAddressCommand.CatalogizeEmailAddress> {

    override fun execute(command: EmailAddressCommand.CatalogizeEmailAddress) {}
}

@Service
@Transactional
internal class UpdateEmailAddressApplicationService(
        private val emailGroupRepository: EmailGroupRepository,
        private val emailAddressRepository: EmailAddressRepository
) : ApplicationService<EmailAddressCommand.UpdateEmailAddress> {

    override fun execute(command: EmailAddressCommand.UpdateEmailAddress) {}
}

