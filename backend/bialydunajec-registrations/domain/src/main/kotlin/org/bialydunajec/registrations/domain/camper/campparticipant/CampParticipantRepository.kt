package org.bialydunajec.registrations.domain.camper.campparticipant

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId

interface CampParticipantRepository : DomainRepository<CampParticipant, CampParticipantId>, CampParticipantReadOnlyRepository {
    fun existsByPeselAndCampRegistrationsEditionId(pesel: Pesel, campRegistrationsEditionId: CampRegistrationsEditionId): Boolean
}
