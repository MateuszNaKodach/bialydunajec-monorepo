package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.base.persistence.ReadOnlyDomainRepository
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusLineId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId

interface CampBusLineReadOnlyRepository : ReadOnlyDomainRepository<CampBusLine, CampBusLineId> {
}