package org.bialydunajec.ddd.domain.persistence

import org.bialydunajec.ddd.domain.valueobject.AggregateId

import java.time.temporal.TemporalAccessor

interface Auditable<U, T : TemporalAccessor> {
    fun getCreatedDate(): T
    fun getCreatedBy(): U?
    fun getLastModifiedDate(): T?
    fun getLastModifiedBy():U?
}