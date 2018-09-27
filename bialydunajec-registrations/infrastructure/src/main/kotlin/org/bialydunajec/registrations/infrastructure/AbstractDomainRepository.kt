package org.bialydunajec.registrations.infrastructure

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.aggregate.DomainRepository
import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
abstract class AbstractDomainRepository<AggregateType : AggregateRoot<*, *>, AggregateIdType : Identifier<*>, RepositoryType: JpaRepository<AggregateType, AggregateIdType>>(
        val jpaRepository: RepositoryType
) : DomainRepository<AggregateType, AggregateIdType> {

    override fun save(aggregateRoot: AggregateType) = jpaRepository.save(aggregateRoot)

    override fun findAll(): Collection<AggregateType> = jpaRepository.findAll()

    override fun findById(aggregateId: AggregateIdType): AggregateType? = jpaRepository.findById(aggregateId).orElse(null)

    override fun delete(aggregateRoot: AggregateType) = jpaRepository.delete(aggregateRoot)

    override fun deleteById(aggregateId: AggregateIdType) = jpaRepository.deleteById(aggregateId)

    override fun count() = jpaRepository.count()

    override fun isEmpty() = count() == 0L
}