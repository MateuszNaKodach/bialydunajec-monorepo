package org.bialydunajec.registrations.application

import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.shirt.CampEditionShirt
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtId
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtReadOnlyRepository

internal class InMemoryCampEditionShirtReadOnlyRepository : CampEditionShirtReadOnlyRepository {

    override fun findByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): CampEditionShirt? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Collection<CampEditionShirt> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(aggregateId: CampEditionShirtId): CampEditionShirt? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByIdAndSpecification(
        aggregateId: CampEditionShirtId,
        specification: Specification<CampEditionShirt>
    ): CampEditionShirt? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findFirstBySpecification(specification: Specification<CampEditionShirt>): CampEditionShirt? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllBySpecification(specification: Specification<CampEditionShirt>): Collection<CampEditionShirt> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(aggregateId: CampEditionShirtId): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByIdAndSpecification(
        aggregateId: CampEditionShirtId,
        specification: Specification<CampEditionShirt>
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
