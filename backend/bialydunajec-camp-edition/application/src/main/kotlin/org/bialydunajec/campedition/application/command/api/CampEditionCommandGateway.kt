package org.bialydunajec.campedition.application.command.api

import org.bialydunajec.campedition.application.command.CreateCampEditionApplicationService
import org.bialydunajec.campedition.application.command.UpdateCampEditionDurationApplicationService
import org.bialydunajec.ddd.application.base.command.CommandProcessor
import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.springframework.transaction.annotation.Transactional

@Transactional
class CampEditionCommandGateway internal constructor(
        private val createCampEditionApplicationService: CreateCampEditionApplicationService,
        private val updateCampEditionDurationApplicationService: UpdateCampEditionDurationApplicationService
) : CommandGateway, CommandProcessor<CampEditionCommand> {

    override fun process(command: CampEditionCommand) =
            when (command) {
                is CampEditionCommand.CreateCampEdition -> process(command)
                is CampEditionCommand.UpdateCampEditionDuration -> process(command)
            }


    fun process(command: CampEditionCommand.CreateCampEdition) =
            createCampEditionApplicationService.execute(command)


    fun process(command: CampEditionCommand.UpdateCampEditionDuration) =
            updateCampEditionDurationApplicationService.execute(command)


}
