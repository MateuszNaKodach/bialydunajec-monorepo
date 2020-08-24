package org.bialydunajec.ddd.domain.base.persistence

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.event.DomainEventBus
import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import java.util.concurrent.ConcurrentHashMap


open class InMemoryDomainRepository<AggregateIdType : Identifier<*>, AggregateType : AggregateRoot<AggregateIdType, *>>(
        private val inMemoryMap: ConcurrentHashMap<AggregateIdType, AggregateType> = ConcurrentHashMap<AggregateIdType, AggregateType>(),
        private val domainEventBus: DomainEventBus
) : InMemoryReadOnlyDomainRepository<AggregateIdType, AggregateType>(inMemoryMap), DomainRepository<AggregateType, AggregateIdType> {

    override fun save(aggregateRoot: AggregateType): AggregateType {
        inMemoryMap[aggregateRoot.getAggregateId()] = aggregateRoot
        domainEventBus.publishAll(aggregateRoot.domainEvents())
        aggregateRoot.clearDomainEvents()
        return aggregateRoot
    }

    override fun delete(aggregateRoot: AggregateType) {
        save(aggregateRoot)
        inMemoryMap.remove(aggregateRoot.getAggregateId())
    }
}
