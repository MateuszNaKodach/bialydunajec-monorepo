package org.bialydunajec.ddd.domain.base.aggregate

import org.bialydunajec.ddd.domain.base.valueobject.Identifier

interface DomainRepository<AggregateType : AggregateRoot<*, *>, AggregateIdType : Identifier<*>> {

    fun save(aggregateRoot: AggregateType):AggregateType
    fun findAll(): Collection<AggregateType>
    fun findById(aggregateId: AggregateIdType): AggregateType?
    fun delete(aggregateRoot: AggregateType)
    fun deleteById(aggregateId: AggregateIdType)
    fun count():Long
    fun isEmpty():Boolean
}