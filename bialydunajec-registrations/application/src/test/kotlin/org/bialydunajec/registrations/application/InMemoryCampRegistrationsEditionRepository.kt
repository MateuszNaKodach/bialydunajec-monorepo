package org.bialydunajec.registrations.application

import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository

internal class InMemoryCampRegistrationsEditionRepository : CampRegistrationsEditionRepository,
    InMemoryRepository<CampRegistrationsEdition>() {

    override fun delete(aggregateRoot: CampRegistrationsEdition) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Collection<CampRegistrationsEdition> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(aggregateId: CampRegistrationsEditionId): CampRegistrationsEdition? {
        return items.find { it.getAggregateId() == aggregateId }
    }

    override fun findByIdAndSpecification(
        aggregateId: CampRegistrationsEditionId,
        specification: Specification<CampRegistrationsEdition>
    ): CampRegistrationsEdition? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findFirstBySpecification(specification: Specification<CampRegistrationsEdition>): CampRegistrationsEdition? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllBySpecification(specification: Specification<CampRegistrationsEdition>): Collection<CampRegistrationsEdition> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(aggregateId: CampRegistrationsEditionId): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByIdAndSpecification(
        aggregateId: CampRegistrationsEditionId,
        specification: Specification<CampRegistrationsEdition>
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
