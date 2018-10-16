package org.bialydunajec.registrations.application.query.readmodel

import org.bialydunajec.registrations.application.dto.*
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.campedition.specification.InProgressCampRegistrationsSpecification
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.cottage.specification.CottageFreeSpaceSpecificationFactory
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
internal class CampRegistrationsDomainModelReader(
        private val campRegistrationsEditionRepository: CampRegistrationsEditionRepository,
        private val academicMinistryRepository: AcademicMinistryRepository,
        private val cottageRepository: CottageRepository,
        private val campParticipantRepository: CampParticipantRepository,
        private val cottageFreeSpaceSpecificationFactory: CottageFreeSpaceSpecificationFactory
) {

    fun readFor(query: CampRegistrationsEditionQuery.All): Collection<CampRegistrationsEditionDto> =
            campRegistrationsEditionRepository.findAll()
                    .map { CampRegistrationsEditionDto.from(it.getSnapshot()) }

    fun readFor(query: CampRegistrationsEditionQuery.ById): CampRegistrationsEditionDto? =
            campRegistrationsEditionRepository
                    .findById(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.let { CampRegistrationsEditionDto.from(it.getSnapshot()) }

    fun readFor(query: CampRegistrationsEditionQuery.InProgress): CampRegistrationsEditionDto? =
            campRegistrationsEditionRepository.findFirstBySpecification(InProgressCampRegistrationsSpecification())
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

    fun readFor(query: CottageQuery.All): Collection<CottageDto> =
            cottageRepository.findAll()
                    .map { CottageDto.from(it.getSnapshot()) }

    fun readFor(query: CottageQuery.ById): CottageDto? =
            cottageRepository.findById(CottageId(query.cottageId))
                    ?.let { CottageDto.from(it.getSnapshot()) }

    fun readFor(query: CottageQuery.AllByCampRegistrationsEditionId): Collection<CottageDto> =
            cottageRepository.findAllByCampRegistrationsEditionId(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    .map { CottageDto.from(it.getSnapshot()) }

    fun readFor(query: CottageQuery.ByIdAndByCampRegistrationsEditionId): CottageDto? =
            cottageRepository.findByIdAndCampRegistrationsEditionId(CottageId(query.cottageId), CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.let { CottageDto.from(it.getSnapshot()) }

    fun readFor(query: CottageQuery.AllActiveByCampRegistrationsEditionId): Collection<CampRegistrationsCottageDto> =
            cottageRepository.findAllByCampRegistrationsEditionIdAndStatus(CampRegistrationsEditionId(query.campRegistrationsEditionId), CottageStatus.ACTIVATED)
                    .map { CampRegistrationsCottageDto.from(it.getSnapshot(), cottageFreeSpaceSpecificationFactory.createFor(query.camperGender).isSatisfiedBy(it)) }

    fun readFor(query: CampParticipantQuery.CountByCottageId) =
            CampParticipantCountByCottageIdDto(query.cottageId, campParticipantRepository.countByCottageId(CottageId(query.cottageId)))

}