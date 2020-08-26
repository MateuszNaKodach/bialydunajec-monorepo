package org.bialydunajec.registrations.infrastructure.camper.campparticipantregistration

import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistration
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationId
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampParticipantRegistrationRepositoryImpl(
        jpaRepository: CampParticipantRegistrationJpaRepository,
        domainEventBus: DomainEventBus
) : AbstractDomainRepositoryImpl<CampParticipantRegistration, CampParticipantRegistrationId, CampParticipantRegistrationJpaRepository>(jpaRepository, domainEventBus), CampParticipantRegistrationRepository {

    override fun findAllByCottageId(cottageId: CottageId) =
            jpaRepository.findAllByCamperApplicationCottageId(cottageId)

    override fun findByCampParticipantId(campParticipantId: CampParticipantId) =
            jpaRepository.findByCampParticipantId(campParticipantId)
}

internal interface CampParticipantRegistrationJpaRepository : JpaRepository<CampParticipantRegistration, CampParticipantRegistrationId> {
    fun findAllByCamperApplicationCottageId(cottageId: CottageId): Collection<CampParticipantRegistration>
    fun findByCampParticipantId(campParticipantId: CampParticipantId): CampParticipantRegistration?
}
