package org.bialydunajec.registrations.infrastructure.camper.campparticipant

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.application.query.readmodel.CampParticipantReadOnlyRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampParticipantRepositoryImpl(
        jpaRepository: CampParticipantJpaRepository
) : AbstractDomainRepositoryImpl<CampParticipant, CampParticipantId, CampParticipantJpaRepository>(jpaRepository),
        CampParticipantRepository, CampParticipantReadOnlyRepository {

    override fun findAll(pageable: Pageable): Page<CampParticipant> =
            jpaRepository.findAll(pageable)

    override fun findAllByCottageId(cottageId: CottageId) =
            jpaRepository.findAllByCurrentCamperDataCottageId(cottageId)

    override fun countByCottageId(cottageId: CottageId): Long =
            jpaRepository.countByCurrentCamperDataCottageId(cottageId)

    override fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long =
            jpaRepository.countByCurrentCamperDataCottageIdAndCurrentCamperDataPersonalDataGender(cottageId, gender)

    override fun findAllByCottageId(cottageId: CottageId, pageable: Pageable): Page<CampParticipant> =
            jpaRepository.findAllByCottageId(cottageId, pageable)

    override fun existsByPeselAndCampRegistrationsEditionId(pesel: Pesel, campRegistrationsEditionId: CampRegistrationsEditionId): Boolean =
            jpaRepository.existsByCurrentCamperDataPersonalDataPeselAndCampRegistrationsEditionId(pesel, campRegistrationsEditionId)
}

internal interface CampParticipantJpaRepository : JpaRepository<CampParticipant, CampParticipantId> {
    fun findAllByCurrentCamperDataCottageId(cottageId: CottageId): Collection<CampParticipant>
    fun findAllByCottageId(cottageId: CottageId, pageable: Pageable): Page<CampParticipant>
    fun countByCurrentCamperDataCottageId(cottageId: CottageId): Long
    fun countByCurrentCamperDataCottageIdAndCurrentCamperDataPersonalDataGender(cottageId: CottageId, gender: Gender): Long
    fun existsByCurrentCamperDataPersonalDataPeselAndCampRegistrationsEditionId(pesel: Pesel, campRegistrationsEditionId: CampRegistrationsEditionId): Boolean
}