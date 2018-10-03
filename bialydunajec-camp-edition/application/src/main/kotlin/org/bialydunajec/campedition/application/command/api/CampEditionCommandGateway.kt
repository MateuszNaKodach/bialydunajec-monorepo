package org.bialydunajec.campedition.application.command.api

import org.bialydunajec.campedition.application.command.CreateCampEditionApplicationService
import org.bialydunajec.campedition.application.command.UpdateCampEditionDurationApplicationService
import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.springframework.stereotype.Component

@Component
internal class CampEditionCommandGateway internal constructor(
        private val createCampEditionApplicationService: CreateCampEditionApplicationService,
        private val updateCampEditionDurationApplicationService: UpdateCampEditionDurationApplicationService
) : CommandGateway {

    fun process(command: CampEditionCommand.CreateCampEdition) {
        createCampEditionApplicationService.process(command)
    }

    fun process(command: CampEditionCommand.UpdateCampEditionDuration) {
        updateCampEditionDurationApplicationService.process(command)
    }

}