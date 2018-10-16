package org.bialydunajec.registrations.infrastructure.camper

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.camper.CampParticipant
import org.bialydunajec.registrations.domain.camper.CampParticipantId
import org.bialydunajec.registrations.domain.camper.CampParticipantRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampParticipantRepositoryImpl(
        jpaRepository: CamperJpaRepository
) : AbstractDomainRepositoryImpl<CampParticipant, CampParticipantId, CamperJpaRepository>(jpaRepository), CampParticipantRepository {

    override fun findAllByCottageId(cottageId: CottageId) =
            jpaRepository.findAllByCurrentCamperDataCottageId(cottageId)

    override fun countByCottageId(cottageId: CottageId): Long =
            jpaRepository.countByCurrentCamperDataCottageId(cottageId)

    override fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long =
            jpaRepository.countByCurrentCamperDataCottageIdAndGender(cottageId, gender)
}

internal interface CamperJpaRepository : JpaRepository<CampParticipant, CampParticipantId> {
    fun findAllByCurrentCamperDataCottageId(cottageId: CottageId): Collection<CampParticipant>
    fun countByCurrentCamperDataCottageId(cottageId: CottageId): Long
    fun countByCurrentCamperDataCottageIdAndGender(cottageId: CottageId, gender: Gender): Long

}