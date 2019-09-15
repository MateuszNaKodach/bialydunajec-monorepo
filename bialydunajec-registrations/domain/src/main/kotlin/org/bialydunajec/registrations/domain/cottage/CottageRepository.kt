package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageStatus

interface CottageRepository : DomainRepository<Cottage, CottageId> {
    fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Collection<Cottage>

    fun findAllByCampRegistrationsEditionIdAndStatus(
        campRegistrationsEditionId: CampRegistrationsEditionId,
        status: CottageStatus
    ): Collection<Cottage>

    fun findByIdAndCampRegistrationsEditionId(
        cottageId: CottageId,
        campRegistrationsEditionId: CampRegistrationsEditionId
    ): Cottage?

    fun countByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Long
    fun findNewestCottageByAcademicMinistryId(academicMinistryId: AcademicMinistryId): Cottage?
}
