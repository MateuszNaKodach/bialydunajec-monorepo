package org.bialydunajec.ddd.infrastructure.base.persistence

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.base.persistence.DomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Identifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

//TODO: Change class declaration to: AbstractJpaDomainRepositoryImpl<AggregateIdType : Identifier<*>, AggregateType : AggregateRoot<AggregateIdType, *>, RepositoryType : JpaRepository<AggregateType, AggregateIdType>>
@NoRepositoryBean
abstract class AbstractDomainRepositoryImpl<AggregateType : AggregateRoot<*, *>, AggregateIdType : Identifier<*>, RepositoryType : JpaRepository<AggregateType, AggregateIdType>>(
        jpaRepository: RepositoryType,
        protected val domainEventBus: DomainEventBus
) : AbstractReadOnlyDomainRepositoryImpl<AggregateType, AggregateIdType, RepositoryType>(jpaRepository), DomainRepository<AggregateType, AggregateIdType> {

    override fun save(aggregateRoot: AggregateType) = jpaRepository.save(aggregateRoot)
            .also {
                domainEventBus.publishAll(it.domainEvents())
                it.clearDomainEvents()
            }

    override fun delete(aggregateRoot: AggregateType) =
            jpaRepository.save(aggregateRoot)
                    .also {
                        domainEventBus.publishAll(it.domainEvents())
                        it.clearDomainEvents()
                    }
                    .let { jpaRepository.delete(it) } //TODO: After class declaration change, replace with deleteById
}
