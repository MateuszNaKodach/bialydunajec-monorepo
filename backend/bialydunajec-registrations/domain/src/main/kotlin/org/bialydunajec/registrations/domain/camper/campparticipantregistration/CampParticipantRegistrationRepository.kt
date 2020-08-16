package org.bialydunajec.registrations.domain.camper.campparticipantregistration

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId

interface CampParticipantRegistrationRepository : DomainRepository<CampParticipantRegistration, CampParticipantRegistrationId> {
    fun findAllByCottageId(cottageId: CottageId): Collection<CampParticipantRegistration>
    fun findByCampParticipantId(campParticipantId: CampParticipantId): CampParticipantRegistration?
}