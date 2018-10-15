package org.bialydunajec.registrations.infrastructure.camper

import org.bialydunajec.registrations.domain.camper.CampParticipant
import org.bialydunajec.registrations.domain.camper.CampParticipantId
import org.bialydunajec.registrations.domain.camper.CampParticipantRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampParticipantRepositoryImpl(
        jpaRepository: CamperJpaRepository
) : AbstractDomainRepositoryImpl<CampParticipant, CampParticipantId, CamperJpaRepository>(jpaRepository), CampParticipantRepository {

}

internal interface CamperJpaRepository : JpaRepository<CampParticipant, CampParticipantId> {
}