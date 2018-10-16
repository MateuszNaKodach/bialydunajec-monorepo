package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.cottage.CottageId

interface CampParticipantRepository : DomainRepository<CampParticipant, CampParticipantId> {
    fun findAllByCottageId(cottageId: CottageId): Collection<CampParticipant>
    fun countByCottageId(cottageId: CottageId): Long
    fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long
}