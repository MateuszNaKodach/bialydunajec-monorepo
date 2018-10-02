package org.bialydunajec.ddd.infrastructure.base.persistence

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
abstract class AbstractDomainRepositoryImpl<AggregateType : AggregateRoot<*, *>, AggregateIdType : Identifier<*>, RepositoryType : JpaRepository<AggregateType, AggregateIdType>>(
        open val jpaRepository: RepositoryType
) : DomainRepository<AggregateType, AggregateIdType> {

    override fun save(aggregateRoot: AggregateType) = jpaRepository.save(aggregateRoot)

    override fun findAll(): Collection<AggregateType> = jpaRepository.findAll()

    override fun findById(aggregateId: AggregateIdType): AggregateType? = jpaRepository.findById(aggregateId).orElse(null)

    override fun findByIdAndSpecification(aggregateId: AggregateIdType, specification: Specification<AggregateType>): AggregateType? =
            findById(aggregateId)?.takeIf { specification.isSatisfiedBy(it) }

    override fun findFirstBySpecification(specification: Specification<AggregateType>): AggregateType? =
            findAll().find { specification.isSatisfiedBy(it) }

    override fun findAllBySpecification(specification: Specification<AggregateType>): Collection<AggregateType> =
            findAll().filter { specification.isSatisfiedBy(it) }

    override fun existsById(aggregateId: AggregateIdType) = jpaRepository.existsById(aggregateId)

    override fun existsByIdAndSpecification(aggregateId: AggregateIdType, specification: Specification<AggregateType>) =
            findByIdAndSpecification(aggregateId, specification) != null

    override fun delete(aggregateRoot: AggregateType) = jpaRepository.delete(aggregateRoot)

    override fun deleteById(aggregateId: AggregateIdType) = jpaRepository.deleteById(aggregateId)

    override fun count() = jpaRepository.count()

    override fun isEmpty() = count() == 0L
}