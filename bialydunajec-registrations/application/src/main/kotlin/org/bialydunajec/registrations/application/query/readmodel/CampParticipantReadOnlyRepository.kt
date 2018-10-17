package org.bialydunajec.registrations.application.query.readmodel

import org.bialydunajec.ddd.domain.base.persistence.ReadOnlyDomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CampParticipantReadOnlyRepository : ReadOnlyDomainRepository<CampParticipant, CampParticipantId> {
    fun findAll(pageable: Pageable): Page<CampParticipant>
    fun findAllByCottageId(cottageId: CottageId): Collection<CampParticipant>
    fun findAllByCottageId(cottageId: CottageId, pageable: Pageable): Page<CampParticipant>
    fun countByCottageId(cottageId: CottageId): Long
    fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long
}