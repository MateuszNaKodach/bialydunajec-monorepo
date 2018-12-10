package org.bialydunajec.ddd.domain.base.aggregate

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.persistence.Auditable
import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing.Auditor
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditableAggregateRoot<AggregateIdType : AggregateId, EventType : DomainEvent<AggregateIdType>>(
        aggregateId: AggregateIdType,
        //@CreatedDate
        private val createdDate: Instant = Instant.now(),

        @CreatedBy
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "auditorId.aggregateId", column = Column(name = "createdBy_auditorId"))
        )
        private var createdBy: Auditor? = null

) : AggregateRoot<AggregateIdType, EventType>(aggregateId), Auditable<Auditor, Instant> {

    @LastModifiedDate
    private var lastModifiedDate: Instant? = null

    @LastModifiedBy
    @Embedded
    @AttributeOverrides(
            AttributeOverride(name = "auditorId.aggregateId", column = Column(name = "lastModifiedBy_auditorId"))
    )
    private var lastModifiedBy: Auditor? = null

    override fun getCreatedDate() = createdDate

    override fun getCreatedBy() = createdBy

    override fun getLastModifiedDate() = lastModifiedDate

    override fun getLastModifiedBy() = lastModifiedBy
}