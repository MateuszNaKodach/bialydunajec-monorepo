package org.bialydunajec.ddd.domain.base.persistence

import java.time.temporal.TemporalAccessor

interface Auditable<U, T : TemporalAccessor> {
    fun getCreatedDate(): T
    fun getCreatedBy(): U?
    fun getLastModifiedDate(): T?
    fun getLastModifiedBy():U?
}