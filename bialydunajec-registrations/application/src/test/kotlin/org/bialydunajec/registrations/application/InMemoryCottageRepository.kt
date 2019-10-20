package org.bialydunajec.registrations.application

import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageStatus

internal class InMemoryCottageRepository : CottageRepository, InMemoryRepository<Cottage>() {

    override fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Collection<Cottage> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllByCampRegistrationsEditionIdAndStatus(
        campRegistrationsEditionId: CampRegistrationsEditionId,
        status: CottageStatus
    ): Collection<Cottage> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByIdAndCampRegistrationsEditionId(
        cottageId: CottageId,
        campRegistrationsEditionId: CampRegistrationsEditionId
    ): Cottage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun countByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findNewestCottageByAcademicMinistryId(academicMinistryId: AcademicMinistryId): Cottage? {
        return items.find { it.getSnapshot().academicMinistryId == academicMinistryId }
    }

    override fun delete(aggregateRoot: Cottage) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Collection<Cottage> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(aggregateId: CottageId): Cottage? {
        return items.find { it.getAggregateId() == aggregateId }
    }

    override fun findByIdAndSpecification(aggregateId: CottageId, specification: Specification<Cottage>): Cottage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findFirstBySpecification(specification: Specification<Cottage>): Cottage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllBySpecification(specification: Specification<Cottage>): Collection<Cottage> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(aggregateId: CottageId): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByIdAndSpecification(aggregateId: CottageId, specification: Specification<Cottage>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
