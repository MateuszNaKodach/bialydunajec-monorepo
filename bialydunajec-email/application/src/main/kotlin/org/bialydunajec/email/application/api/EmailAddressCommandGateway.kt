package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.email.application.*
import org.bialydunajec.email.application.CatalogizeEmailAddressApplicationService
import org.bialydunajec.email.application.UpdateEmailAddressApplicationService
import org.springframework.stereotype.Component

@Component
class EmailAddressCommandGateway internal constructor(
        private val catalogizeEmailAddressApplicationService: CatalogizeEmailAddressApplicationService,
        private val updateEmailAddressApplicationService: UpdateEmailAddressApplicationService,
        private val deactivateEmailAddressApplicationService: DeactivateEmailAddressApplicationService
) : CommandGateway {

    fun process(command: EmailAddressCommand.CatalogizeEmailAddress) =
            catalogizeEmailAddressApplicationService.execute(command)

    fun process(command: EmailAddressCommand.UpdateEmailAddress) =
            updateEmailAddressApplicationService.execute(command)

    fun process(command: EmailAddressCommand.DeactivateEmailAddress) =
            deactivateEmailAddressApplicationService.execute(command)
}