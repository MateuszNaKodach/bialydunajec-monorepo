package org.bialydunajec.registrations.domain.camper.campparticipant

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId

interface CampParticipantRepository : DomainRepository<CampParticipant, CampParticipantId> {
    fun findAllByCottageId(cottageId: CottageId): Collection<CampParticipant>
    fun countByCottageId(cottageId: CottageId): Long
    fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long
    fun existsByPeselAndCampRegistrationsEditionId(pesel: Pesel, campRegistrationsEditionId: CampRegistrationsEditionId): Boolean
}