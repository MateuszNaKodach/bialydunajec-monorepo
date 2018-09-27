package org.bialydunajec.ddd.domain.base.aggregate

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.persistence.Auditable
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing.Auditor
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.*

@MappedSuperclass
abstract class AuditableAggregateRoot<AggregateIdType : AggregateId, EventType : DomainEvent<AggregateIdType>>(
        aggregateId: AggregateIdType,
        @CreatedDate
        private val createdDate: Instant = Instant.now(),

        @CreatedBy
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "auditorId", column = Column(name = "createdBy_auditorId")),
                AttributeOverride(name = "firstName", column = Column(name = "createdBy_firstName")),
                AttributeOverride(name = "lastName", column = Column(name = "createdBy_lastName"))
        )
        private var createdBy: Auditor? = null

) : AggregateRoot<AggregateIdType, EventType>(aggregateId), Auditable<Auditor, Instant>, Versioned {

    @Version
    private var version: Long = 1

    @LastModifiedDate
    private var lastModifiedDate: Instant? = null

    @LastModifiedBy
    @Embedded
    @AttributeOverrides(
            AttributeOverride(name = "auditorId", column = Column(name = "lastModifiedBy_auditorId")),
            AttributeOverride(name = "firstName", column = Column(name = "lastModifiedBy_firstName")),
            AttributeOverride(name = "lastName", column = Column(name = "lastModifiedBy_lastName"))
    )
    private var lastModifiedBy: Auditor? = null

    override fun getCreatedDate() = createdDate

    override fun getCreatedBy() = createdBy

    override fun getLastModifiedDate() = lastModifiedDate

    override fun getLastModifiedBy() = lastModifiedBy

    override fun getVersion() = version
}