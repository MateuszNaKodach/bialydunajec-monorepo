package org.bialydunajec.registrations.application.query.readmodel

import org.bialydunajec.registrations.application.dto.*
import org.bialydunajec.registrations.application.query.api.*
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.campedition.specification.InProgressCampRegistrationsSpecification
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantReadOnlyRepository
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.cottage.specification.CottageFreeSpaceSpecificationFactory
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageStatus
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtReadOnlyRepository
import org.bialydunajec.registrations.dto.*
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
internal class CampRegistrationsDomainModelReader(
        private val campRegistrationsEditionRepository: CampRegistrationsEditionRepository,
        private val academicMinistryRepository: AcademicMinistryRepository,
        private val cottageRepository: CottageRepository,
        private val campParticipantRepository: CampParticipantReadOnlyRepository,
        private val campEditionShirtRepository: CampEditionShirtReadOnlyRepository,
        private val cottageFreeSpaceSpecificationFactory: CottageFreeSpaceSpecificationFactory
) {


    fun readFor(query: CampRegistrationsEditionQuery.All): Collection<CampRegistrationsEditionDto> =
            campRegistrationsEditionRepository.findAll()
                    .map { it.getSnapshot().toCampRegistrationsEditionDto() }

    fun readFor(query: CampRegistrationsEditionQuery.ById): CampRegistrationsEditionDto? =
            campRegistrationsEditionRepository
                    .findById(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.let { it.getSnapshot().toCampRegistrationsEditionDto() }

    fun readFor(query: CampRegistrationsEditionQuery.InProgress): CampRegistrationsEditionDto? =
            campRegistrationsEditionRepository.findFirstBySpecification(InProgressCampRegistrationsSpecification())
                    ?.let { it.getSnapshot().toCampRegistrationsEditionDto() }


    fun readFor(query: CampEditionQuery.All): Collection<CampEditionDto> =
            campRegistrationsEditionRepository.findAll()
                    .map { it.getSnapshot().toDto() }

    fun readFor(query: CampEditionQuery.ById): CampEditionDto? =
            campRegistrationsEditionRepository
                    .findById(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.let { it.getSnapshot().toDto() }


    fun readFor(query: AcademicMinistryQuery.All): Collection<AcademicMinistryDto> =
            academicMinistryRepository.findAll()
                    .map { it.getSnapshot().toDto() }

    fun readFor(query: AcademicMinistryQuery.ById): AcademicMinistryDto? =
            academicMinistryRepository
                    .findById(AcademicMinistryId(query.academicMinistryId))
                    ?.let { it.getSnapshot().toDto() }


    fun readFor(query: CottageQuery.All): Collection<CottageDto> =
            cottageRepository.findAll()
                    .map { it.getSnapshot().toDto() }

    fun readFor(query: CottageQuery.ById): CottageDto? =
            cottageRepository.findById(CottageId(query.cottageId))
                    ?.let { it.getSnapshot().toDto() }

    fun readFor(query: CottageQuery.AllByCampRegistrationsEditionId): Collection<CottageDto> =
            cottageRepository.findAllByCampRegistrationsEditionId(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    .map { it.getSnapshot().toDto() }

    fun readFor(query: CottageQuery.ByIdAndByCampRegistrationsEditionId): CottageDto? =
            cottageRepository.findByIdAndCampRegistrationsEditionId(CottageId(query.cottageId), CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.let { it.getSnapshot().toDto() }

    fun readFor(query: CottageQuery.AllActiveByCampRegistrationsEditionId): Collection<CampRegistrationsCottageDto> =
            cottageRepository.findAllByCampRegistrationsEditionIdAndStatus(CampRegistrationsEditionId(query.campRegistrationsEditionId), CottageStatus.ACTIVATED)
                    .map { CampRegistrationsCottageDto.from(it.getSnapshot(), cottageFreeSpaceSpecificationFactory.createFor(query.camperGender).isSatisfiedBy(it)) }

    fun readFor(query: CottageQuery.NewestByAcademicMinistryId): CottageInfoDto? =
            cottageRepository.findNewestCottageByAcademicMinistryId(AcademicMinistryId(query.academicMinistryId))
                    ?.getSnapshot()
                    ?.let { it.toCottageInfoDto() }

    fun readFor(query: CampParticipantQuery.ById) =
            campParticipantRepository.findById(CampParticipantId(query.campParticipantId))
                    ?.let { campParticipantDto(it) }

    fun readFor(query: CampParticipantQuery.CountByCottageId) =
            CampParticipantCountByCottageIdDto(query.cottageId, campParticipantRepository.countByCottageId(CottageId(query.cottageId)))

    fun readFor(query: CampParticipantQuery.All, pageable: Pageable) =
            campParticipantRepository.findAll(pageable)
                    .map { campParticipantDto(it) }

    private fun campParticipantDto(it: CampParticipant): CampParticipantDto {
        return CampParticipantDto.from(
                it.getSnapshot(),
                it.getSnapshot().confirmedApplication?.let { cottageRepository.findById(it.cottageId)?.getSnapshot() },
                it.getSnapshot().currentCamperData.let { cottageRepository.findById(it.cottageId)!!.getSnapshot() }
        )
    }

    fun readFor(query: CampParticipantQuery.ByCottageId, pageable: Pageable) =
            campParticipantRepository.findAllByCottageId(CottageId(query.cottageId), pageable)
                    .map { campParticipantDto(it) }

    fun readFor(query: CampParticipantQuery.ByCampRegistrationsEditionId, pageable: Pageable) =
            campParticipantRepository.findAllByCampRegistrationsEditionId(CampRegistrationsEditionId(query.campRegistrationsEditionId), pageable)
                    .map { campParticipantDto(it) }

    fun readFor(query: CampEditionShirtQuery.ByCampRegistrationsEditionId): CampEditionShirtDto? =
            campEditionShirtRepository.findByCampRegistrationsEditionId(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.getSnapshot()?.toDto()

    fun readFor(query: CampEditionShirtQuery.AvailableSizesByCampRegistrationsEditionId): Collection<ShirtSizeOptionDto> =
            campEditionShirtRepository.findByCampRegistrationsEditionId(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.getSizeOptions()
                    ?.map { it.toDto() } ?: emptyList()


    fun readFor(query: CampEditionShirtQuery.AvailableColorsByCampRegistrationsEditionId): Collection<ShirtColorOptionDto> =
            campEditionShirtRepository.findByCampRegistrationsEditionId(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.getColorOptions()
                    ?.map { it.toDto() } ?: emptyList()

    fun readFor(query: CampEditionShirtOrderQuery.AllByCampRegistrationsEditionId): CampEditionShirtDto? =
            campEditionShirtRepository.findByCampRegistrationsEditionId(CampRegistrationsEditionId(query.campRegistrationsEditionId))
                    ?.getSnapshot()?.toDto()

}