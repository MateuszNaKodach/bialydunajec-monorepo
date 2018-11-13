package org.bialydunajec.ddd.domain.base.persistence

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.base.valueobject.Identifier

interface DomainRepository<AggregateType : AggregateRoot<*, *>, AggregateIdType : Identifier<*>> : ReadOnlyDomainRepository<AggregateType, AggregateIdType> {

    fun save(aggregateRoot: AggregateType): AggregateType
    fun delete(aggregateRoot: AggregateType)
    fun deleteById(aggregateId: AggregateIdType)

}