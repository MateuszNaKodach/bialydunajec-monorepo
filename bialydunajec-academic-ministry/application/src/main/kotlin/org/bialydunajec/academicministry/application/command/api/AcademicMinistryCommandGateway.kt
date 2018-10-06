package org.bialydunajec.academicministry.application.command.api

import org.bialydunajec.academicministry.application.command.CreateAcademicMinistryApplicationService
import org.bialydunajec.academicministry.application.command.UpdateAcademicMinistryApplicationService
import org.bialydunajec.ddd.application.base.command.CommandGateway

class AcademicMinistryCommandGateway internal constructor(
        private val createAcademicMinistryApplicationService: CreateAcademicMinistryApplicationService,
        private val updateAcademicMinistryApplicationService: UpdateAcademicMinistryApplicationService
) : CommandGateway {

    fun process(command: AcademicMinistryCommand.CreateAcademicMinistry) =
            createAcademicMinistryApplicationService.process(command)

    fun process(command: AcademicMinistryCommand.UpdateAcademicMinistry) =
            updateAcademicMinistryApplicationService.process(command)

}