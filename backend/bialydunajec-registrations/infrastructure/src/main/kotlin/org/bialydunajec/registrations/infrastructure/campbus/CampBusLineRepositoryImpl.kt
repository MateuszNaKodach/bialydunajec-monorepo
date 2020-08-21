package org.bialydunajec.registrations.infrastructure.campbus

import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.bialydunajec.registrations.domain.campbus.CampBusLine
import org.bialydunajec.registrations.domain.campbus.CampBusLineRepository
import org.bialydunajec.registrations.domain.campbus.valueobject.CampBusLineId
import org.springframework.data.jpa.repository.JpaRepository

internal class CampBusLineRepositoryImpl(
        jpaRepository: CampBusLineJpaRepository
) : AbstractDomainRepositoryImpl<CampBusLine, CampBusLineId, CampBusLineJpaRepository>(jpaRepository), CampBusLineRepository {

}

internal interface CampBusLineJpaRepository : JpaRepository<CampBusLine, CampBusLineId>