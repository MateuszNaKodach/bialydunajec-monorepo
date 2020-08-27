package org.bialydunajec.ddd.domain.base.persistence

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Identifier
import java.util.concurrent.ConcurrentHashMap


open class InMemoryReadOnlyDomainRepository<AggregateIdType : Identifier<*>, AggregateType : AggregateRoot<AggregateIdType, *>>(
        private val inMemoryMap: ConcurrentHashMap<AggregateIdType, AggregateType> = ConcurrentHashMap<AggregateIdType, AggregateType>()
) : ReadOnlyDomainRepository<AggregateType, AggregateIdType> {


    override fun findAll(): Collection<AggregateType> {
        return inMemoryMap.values
    }

    override fun findById(aggregateId: AggregateIdType): AggregateType? {
        return inMemoryMap[aggregateId]
    }

    override fun findByIdAndSpecification(aggregateId: AggregateIdType, specification: Specification<AggregateType>): AggregateType? {
        val foundById = inMemoryMap[aggregateId]
        return if (foundById != null && specification.isSatisfiedBy(foundById)) foundById else null
    }

    override fun findFirstBySpecification(specification: Specification<AggregateType>): AggregateType? {
        return inMemoryMap.values.find { specification.isSatisfiedBy(it) }
    }

    override fun findAllBySpecification(specification: Specification<AggregateType>): Collection<AggregateType> {
        return inMemoryMap.values.filter { specification.isSatisfiedBy(it) }
    }

    override fun existsById(aggregateId: AggregateIdType): Boolean {
        return inMemoryMap[aggregateId] != null
    }

    override fun existsByIdAndSpecification(aggregateId: AggregateIdType, specification: Specification<AggregateType>): Boolean {
        return findByIdAndSpecification(aggregateId, specification) != null
    }

    override fun count(): Long {
        return inMemoryMap.mappingCount()
    }

    override fun isEmpty(): Boolean {
        return inMemoryMap.isEmpty()
    }
}
