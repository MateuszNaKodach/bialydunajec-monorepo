package org.bialydunajec.ddd.infrastructure.base.persistence

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
abstract class AbstractDomainRepositoryImpl<AggregateType : AggregateRoot<*, *>, AggregateIdType : Identifier<*>, RepositoryType : JpaRepository<AggregateType, AggregateIdType>>(
        jpaRepository: RepositoryType
) : AbstractReadOnlyDomainRepositoryImpl<AggregateType, AggregateIdType, RepositoryType>(jpaRepository), DomainRepository<AggregateType, AggregateIdType> {

    override fun save(aggregateRoot: AggregateType) = jpaRepository.save(aggregateRoot)

    override fun delete(aggregateRoot: AggregateType) = jpaRepository.save(aggregateRoot).let {
        jpaRepository.delete(aggregateRoot)
    }

    override fun deleteById(aggregateId: AggregateIdType) = jpaRepository.deleteById(aggregateId)
}