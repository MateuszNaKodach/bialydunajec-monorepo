package org.bialydunajec.ddd.domain.base.aggregate

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId

interface DomainRepository<AggregateType : AggregateRoot<*, *>, AggregateIdType : AggregateId> {

    fun save(aggregateRoot: AggregateType):AggregateType
    fun findAll(): Collection<AggregateType>
    fun findById(aggregateId: AggregateIdType): AggregateType?
    fun delete(aggregateRoot: AggregateType)
    fun deleteById(aggregateId: AggregateIdType)
    fun count():Long
    fun isEmpty():Boolean
}