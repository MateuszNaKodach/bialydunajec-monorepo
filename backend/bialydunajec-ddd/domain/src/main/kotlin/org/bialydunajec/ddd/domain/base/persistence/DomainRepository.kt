package org.bialydunajec.ddd.domain.base.persistence

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Identifier

interface DomainRepository<AggregateType : AggregateRoot<*, *>, AggregateIdType : Identifier<*>> : ReadOnlyDomainRepository<AggregateType, AggregateIdType> {

    fun save(aggregateRoot: AggregateType): AggregateType
    fun delete(aggregateRoot: AggregateType)

}
