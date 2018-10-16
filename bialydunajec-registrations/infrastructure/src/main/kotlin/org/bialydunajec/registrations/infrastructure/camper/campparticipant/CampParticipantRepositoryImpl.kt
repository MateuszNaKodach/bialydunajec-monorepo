package org.bialydunajec.registrations.infrastructure.camper.campparticipant

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampParticipantRepositoryImpl(
        jpaRepository: CampParticipantJpaRepository
) : AbstractDomainRepositoryImpl<CampParticipant, CampParticipantId, CampParticipantJpaRepository>(jpaRepository), CampParticipantRepository {

    override fun findAllByCottageId(cottageId: CottageId) =
            jpaRepository.findAllByCurrentCamperDataCottageId(cottageId)

    override fun countByCottageId(cottageId: CottageId): Long =
            jpaRepository.countByCurrentCamperDataCottageId(cottageId)

    override fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long =
            jpaRepository.countByCurrentCamperDataCottageIdAndCurrentCamperDataPersonalDataGender(cottageId, gender)
}

internal interface CampParticipantJpaRepository : JpaRepository<CampParticipant, CampParticipantId> {
    fun findAllByCurrentCamperDataCottageId(cottageId: CottageId): Collection<CampParticipant>
    fun countByCurrentCamperDataCottageId(cottageId: CottageId): Long
    fun countByCurrentCamperDataCottageIdAndCurrentCamperDataPersonalDataGender(cottageId: CottageId, gender: Gender): Long

}