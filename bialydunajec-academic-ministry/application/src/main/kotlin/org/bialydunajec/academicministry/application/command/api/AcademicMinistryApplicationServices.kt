package org.bialydunajec.academicministry.application.command.api

import org.bialydunajec.academicministry.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.ddd.application.base.ApplicationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CreateAcademicMinistryApplicationService(
        private val academicMinistryRepository: AcademicMinistryRepository
) : ApplicationService<AcademicMinistryCommand.CreateAcademicMinistry> {

    override fun process(command: AcademicMinistryCommand.CreateAcademicMinistry) {

    }
}

@Service
@Transactional
internal class UpdateAcademicMinistryApplicationService(
        private val academicMinistryRepository: AcademicMinistryRepository
) : ApplicationService<AcademicMinistryCommand.UpdateAcademicMinistry> {

    override fun process(command: AcademicMinistryCommand.UpdateAcademicMinistry) {

    }
}