package org.bialydunajec.registrations.infrastructure.campbus

import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.campbus.CampBusSeatReservations
import org.bialydunajec.registrations.domain.campbus.CampBusSeatReservationsRepository
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusSeatReservationsId
import org.springframework.data.jpa.repository.JpaRepository

internal class CampBusSeatReservationsRepositoryImpl(
        jpaRepository: CampBusSeatReservationsJpaRepository,
        domainEventBus: DomainEventBus
) : AbstractDomainRepositoryImpl<CampBusSeatReservations, CampBusSeatReservationsId, CampBusSeatReservationsJpaRepository>(jpaRepository, domainEventBus), CampBusSeatReservationsRepository {

}

internal interface CampBusSeatReservationsJpaRepository : JpaRepository<CampBusSeatReservations, CampBusSeatReservationsId>
