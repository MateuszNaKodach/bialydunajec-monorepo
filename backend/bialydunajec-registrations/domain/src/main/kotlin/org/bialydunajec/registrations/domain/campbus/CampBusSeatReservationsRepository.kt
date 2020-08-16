package org.bialydunajec.registrations.domain.campbus

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusSeatReservationsId

interface CampBusSeatReservationsRepository
    : DomainRepository<CampBusSeatReservations, CampBusSeatReservationsId>, CampBusSeatReservationsReadOnlyRepository {
}