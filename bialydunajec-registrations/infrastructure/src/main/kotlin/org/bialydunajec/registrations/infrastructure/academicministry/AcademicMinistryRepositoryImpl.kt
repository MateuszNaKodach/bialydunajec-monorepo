package org.bialydunajec.registrations.infrastructure.academicministry

import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class AcademicMinistryRepositoryImpl(
        jpaRepository: AcademicMinistryJpaRepository
) : AbstractDomainRepositoryImpl<AcademicMinistry, AcademicMinistryId, AcademicMinistryJpaRepository>(jpaRepository), AcademicMinistryRepository {

}

internal interface AcademicMinistryJpaRepository : JpaRepository<AcademicMinistry, AcademicMinistryId>