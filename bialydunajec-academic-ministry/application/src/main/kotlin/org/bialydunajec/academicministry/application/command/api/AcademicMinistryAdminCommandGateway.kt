package org.bialydunajec.academicministry.application.command.api

import org.bialydunajec.academicministry.application.command.CreateAcademicMinistryApplicationService
import org.bialydunajec.academicministry.application.command.CreateAcademicMinistryPriestApplicationService
import org.bialydunajec.academicministry.application.command.UpdateAcademicMinistryApplicationService
import org.bialydunajec.ddd.application.base.command.CommandGateway
import org.springframework.stereotype.Service

@Service
class AcademicMinistryAdminCommandGateway internal constructor(
        private val createAcademicMinistryApplicationService: CreateAcademicMinistryApplicationService,
        private val updateAcademicMinistryApplicationService: UpdateAcademicMinistryApplicationService,
        private val createAcademicMinistryPriestApplicationService: CreateAcademicMinistryPriestApplicationService
) : CommandGateway {

    fun process(command: AcademicMinistryCommand.CreateAcademicMinistry) =
            createAcademicMinistryApplicationService.process(command)

    fun process(command: AcademicMinistryCommand.UpdateAcademicMinistry) =
            updateAcademicMinistryApplicationService.process(command)

    fun process(command: AcademicMinistryCommand.CreateAcademicMinistryPriest) =
            createAcademicMinistryPriestApplicationService.process(command)

}