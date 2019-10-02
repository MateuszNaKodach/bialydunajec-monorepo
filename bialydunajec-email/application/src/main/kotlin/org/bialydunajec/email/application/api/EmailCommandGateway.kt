package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.email.application.CatalogizeEmailAddressApplicationService
import org.bialydunajec.email.application.CorrectEmailAddressOwnerApplicationService
import org.springframework.stereotype.Component

@Component
class EmailCommandGateway internal constructor(
        private val catalogizeEmailAddressApplicationService: CatalogizeEmailAddressApplicationService,
        private val updateEmailAddressApplicationService: CorrectEmailAddressOwnerApplicationService
) : CommandGateway {

    fun process(command: EmailAddressCommand.CatalogizeEmailAddress) =
            catalogizeEmailAddressApplicationService.execute(command)

    fun process(command: EmailAddressCommand.UpdateEmailAddressOwner) =
            updateEmailAddressApplicationService.execute(command)

}
