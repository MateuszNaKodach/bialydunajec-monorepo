package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.bialydunajec.email.application.CatalogizeEmailAddressApplicationService
import org.bialydunajec.email.application.ChangeEmailAddressApplicationService
import org.bialydunajec.email.application.CorrectEmailOwnerApplicationService
import org.springframework.stereotype.Component

@Component
class EmailCommandGateway internal constructor(
    private val catalogizeEmailAddressApplicationService: CatalogizeEmailAddressApplicationService,
    private val correctEmailOwnerApplicationService: CorrectEmailOwnerApplicationService,
    private val changeEmailAddressApplicationService: ChangeEmailAddressApplicationService
) : CommandGateway {

    fun process(command: EmailCommand.CatalogizeEmail) =
        catalogizeEmailAddressApplicationService.execute(command)

    fun process(command: EmailCommand.CorrectEmailOwner) =
        correctEmailOwnerApplicationService.execute(command)

    fun process(command: EmailCommand.ChangeEmailAddress) =
        changeEmailAddressApplicationService.execute(command)

}
