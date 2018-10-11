package org.bialydunajec.registrations.application.query.readmodel

import org.bialydunajec.registrations.application.query.api.AcademicMinistryQuery
import org.bialydunajec.registrations.application.query.api.CampEditionQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsEditionQuery
import org.bialydunajec.registrations.application.query.api.dto.AcademicMinistryDto
import org.bialydunajec.registrations.application.query.api.dto.CampEditionDto
import org.bialydunajec.registrations.application.query.api.dto.CampRegistrationsEditionDto
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
internal class CampRegistrationsDomainModelReader(
        private val campRegistrationsEditionRepository: CampRegistrationsEditionRepository,
        private val academicMinistryRepository: AcademicMinistryRepository) {

    fun readFor(query: CampRegistrationsEditionQuery.All): Collection<CampRegistrationsEditionDto> =
            campRegistrationsEditionRepository.findAll()
                    .map { CampRegistrationsEditionDto.from(it.getSnapshot()) }

    fun readFor(query: CampRegistrationsEditionQuery.ById): CampRegistrationsEditionDto? =
            campRegistrationsEditionRepository
                    .findById(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.let { CampRegistrationsEditionDto.from(it.getSnapshot()) }

    fun readFor(query: CampEditionQuery.All): Collection<CampEditionDto> =
            campRegistrationsEditionRepository.findAll()
                    .map { CampEditionDto.from(it.getSnapshot()) }

    fun readFor(query: CampEditionQuery.ById): CampEditionDto? =
            campRegistrationsEditionRepository
                    .findById(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.let { CampEditionDto.from(it.getSnapshot()) }

    fun readFor(query: AcademicMinistryQuery.All): Collection<AcademicMinistryDto> =
            academicMinistryRepository.findAll()
                    .map { AcademicMinistryDto.from(it.getSnapshot()) }

    fun readFor(query: AcademicMinistryQuery.ById): AcademicMinistryDto? =
            academicMinistryRepository
                    .findById(AcademicMinistryId(query.academicMinistryId))
                    ?.let { AcademicMinistryDto.from(it.getSnapshot()) }
}