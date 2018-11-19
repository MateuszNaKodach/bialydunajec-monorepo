package org.bialydunajec.registrations.infrastructure.campbus

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.campbus.CampBusSeatReservations
import org.bialydunajec.registrations.domain.campbus.CampBusSeatReservationsRepository
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusSeatReservationsId
import org.springframework.data.jpa.repository.JpaRepository

internal class CampBusSeatReservationsRepositoryImpl(
        jpaRepository: CampBusSeatReservationsJpaRepository
) : AbstractDomainRepositoryImpl<CampBusSeatReservations, CampBusSeatReservationsId, CampBusSeatReservationsJpaRepository>(jpaRepository), CampBusSeatReservationsRepository {

}

internal interface CampBusSeatReservationsJpaRepository : JpaRepository<CampBusSeatReservations, CampBusSeatReservationsId>