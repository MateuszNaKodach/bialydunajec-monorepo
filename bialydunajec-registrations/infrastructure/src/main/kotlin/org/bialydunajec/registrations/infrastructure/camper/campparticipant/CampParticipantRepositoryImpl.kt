package org.bialydunajec.registrations.infrastructure.camper.campparticipant

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantReadOnlyRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

const val CAMP_PARTICIPANT_CACHE = "org.bialydunajec.campregistrations.CAMP_PARTICIPANT_CACHE"


@Repository
internal class CampParticipantRepositoryImpl(
        jpaRepository: CampParticipantJpaRepository
) : AbstractDomainRepositoryImpl<CampParticipant, CampParticipantId, CampParticipantJpaRepository>(jpaRepository),
        CampParticipantRepository, CampParticipantReadOnlyRepository {

    @Cacheable(cacheNames = [CAMP_PARTICIPANT_CACHE], key = "{#root.methodName}")
    override fun findAll(): Collection<CampParticipant> = super.findAll()

    @Cacheable(cacheNames = [CAMP_PARTICIPANT_CACHE], key = "{#root.methodName, #pageable}")
    override fun findAll(pageable: Pageable): Page<CampParticipant> =
            jpaRepository.findAll(pageable)

    override fun findAllByCottageId(cottageId: CottageId) =
            jpaRepository.findAllByCurrentCamperDataCottageId(cottageId)

    // @Cacheable(cacheNames = [CAMP_PARTICIPANT_CACHE], key = "{#root.methodName, #cottageId}")
    override fun countByCottageId(cottageId: CottageId): Long =
            jpaRepository.countByCurrentCamperDataCottageId(cottageId)

    // @Cacheable(cacheNames = [CAMP_PARTICIPANT_CACHE], key = "{#root.methodName, #cottageId, #gender}")
    override fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long =
            jpaRepository.countByCurrentCamperDataCottageIdAndCurrentCamperDataPersonalDataGender(cottageId, gender)

    @Cacheable(cacheNames = [CAMP_PARTICIPANT_CACHE], key = "{#root.methodName, #cottageId, #pageable}")
    override fun findAllByCottageId(cottageId: CottageId, pageable: Pageable): Page<CampParticipant> =
            jpaRepository.findAllByCottageId(cottageId, pageable)

    @Cacheable(cacheNames = [CAMP_PARTICIPANT_CACHE], key = "{#root.methodName, #campRegistrationsEditionId, #pageable}")
    override fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId, pageable: Pageable): Page<CampParticipant>
            = jpaRepository.findAllByCampRegistrationsEditionId(campRegistrationsEditionId, pageable)

    override fun existsByPeselAndCampRegistrationsEditionId(pesel: Pesel, campRegistrationsEditionId: CampRegistrationsEditionId): Boolean =
            jpaRepository.existsByCurrentCamperDataPersonalDataPeselAndCampRegistrationsEditionId(pesel, campRegistrationsEditionId)


    @CacheEvict(cacheNames = [CAMP_PARTICIPANT_CACHE], allEntries = true)
    override fun save(aggregateRoot: CampParticipant) =
            super.save(aggregateRoot)

}

internal interface CampParticipantJpaRepository : JpaRepository<CampParticipant, CampParticipantId> {
    fun findAllByCurrentCamperDataCottageId(cottageId: CottageId): Collection<CampParticipant>
    fun findAllByCottageId(cottageId: CottageId, pageable: Pageable): Page<CampParticipant>
    fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId, pageable: Pageable): Page<CampParticipant>
    fun countByCurrentCamperDataCottageId(cottageId: CottageId): Long
    fun countByCurrentCamperDataCottageIdAndCurrentCamperDataPersonalDataGender(cottageId: CottageId, gender: Gender): Long
    fun existsByCurrentCamperDataPersonalDataPeselAndCampRegistrationsEditionId(pesel: Pesel, campRegistrationsEditionId: CampRegistrationsEditionId): Boolean
}