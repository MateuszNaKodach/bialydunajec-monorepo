package org.bialydunajec.academicministry.application.command.api

import org.bialydunajec.academicministry.application.command.CreateAcademicMinistryApplicationService
import org.bialydunajec.academicministry.application.command.CreateAcademicMinistryPriestApplicationService
import org.bialydunajec.academicministry.application.command.RemoveAcademicMinistryPriestApplicationService
import org.bialydunajec.academicministry.application.command.UpdateAcademicMinistryApplicationService
import org.bialydunajec.ddd.application.base.command.CommandGateway

class AcademicMinistryAdminCommandGateway internal constructor(
        private val createAcademicMinistryApplicationService: CreateAcademicMinistryApplicationService,
        private val updateAcademicMinistryApplicationService: UpdateAcademicMinistryApplicationService,
        private val createAcademicMinistryPriestApplicationService: CreateAcademicMinistryPriestApplicationService,
        private val removeAcademicMinistryPriestApplicationService: RemoveAcademicMinistryPriestApplicationService
) : CommandGateway {

    fun process(command: AcademicMinistryCommand.CreateAcademicMinistry) =
            createAcademicMinistryApplicationService.execute(command)

    fun process(command: AcademicMinistryCommand.UpdateAcademicMinistry) =
            updateAcademicMinistryApplicationService.execute(command)

    fun process(command: AcademicMinistryCommand.CreateAcademicMinistryPriest) =
            createAcademicMinistryPriestApplicationService.execute(command)

    fun process(command: AcademicMinistryCommand.RemoveAcademicMinistryPriest) =
            removeAcademicMinistryPriestApplicationService.execute(command)

}
