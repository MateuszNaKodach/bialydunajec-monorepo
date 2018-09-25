package org.bialydunajec.ddd.domain.base.aggregate

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.persistence.Auditable
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
abstract class AuditableAggregateRoot<AggregateIdType : AggregateId, EventType : DomainEvent<AggregateIdType>>(
        aggregateId: AggregateIdType,
        @CreatedDate
        private val createdDate: Instant = Instant.now(),

        @CreatedBy
        private var createdBy: AggregateIdType? = null

) : AggregateRoot<AggregateIdType, EventType>(aggregateId), Auditable<AggregateIdType, Instant>, Versioned {

    @Version
    private var version: Long = 1

    @LastModifiedDate
    private var lastModifiedDate: Instant? = null

    @LastModifiedBy
    private var lastModifiedBy: AggregateIdType? = null

    override fun getCreatedDate() = createdDate

    override fun getCreatedBy() = createdBy

    override fun getLastModifiedDate() = lastModifiedDate

    override fun getLastModifiedBy() = lastModifiedBy

    override fun getVersion() = version
}