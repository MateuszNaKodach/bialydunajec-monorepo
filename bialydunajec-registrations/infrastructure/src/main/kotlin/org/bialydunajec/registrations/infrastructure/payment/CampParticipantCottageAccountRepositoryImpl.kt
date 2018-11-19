package org.bialydunajec.registrations.infrastructure.payment

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccount
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountId
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountReadOnlyRepository
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class CampParticipantCottageAccountRepositoryImpl(
        jpaRepository: CampParticipantCottageAccountJpaRepository
) : AbstractDomainRepositoryImpl<CampParticipantCottageAccount, CampParticipantCottageAccountId, CampParticipantCottageAccountJpaRepository>(jpaRepository),
        CampParticipantCottageAccountRepository {

    override fun findByCampParticipantIdAndCottageId(campParticipantId: CampParticipantId, cottageId: CottageId) = jpaRepository.findByCampParticipantIdAndCottageId(campParticipantId, cottageId)
}

internal interface CampParticipantCottageAccountJpaRepository : JpaRepository<CampParticipantCottageAccount, CampParticipantCottageAccountId> {

    fun findByCampParticipantIdAndCottageId(campParticipantId: CampParticipantId, cottageId: CottageId): CampParticipantCottageAccount?
}