package org.bialydunajec.academicministry.infrastructure

import org.bialydunajec.academicministry.domain.AcademicMinistry
import org.bialydunajec.academicministry.domain.AcademicMinistryId
import org.bialydunajec.academicministry.domain.AcademicMinistryRepository
import org.bialydunajec.ddd.infrastructure.base.persistence.AbstractDomainRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class AcademicMinistryRepositoryImpl(
        jpaRepository: AcademicMinistryJpaRepository
) : AbstractDomainRepositoryImpl<AcademicMinistry, AcademicMinistryId, AcademicMinistryJpaRepository>(jpaRepository), AcademicMinistryRepository {

}

internal interface AcademicMinistryJpaRepository : JpaRepository<AcademicMinistry, AcademicMinistryId>