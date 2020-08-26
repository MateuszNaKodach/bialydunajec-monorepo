package org.bialydunajec.ddd.domain.base.persistence

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.EntityId
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AuditableEntity<EntityIdType: EntityId>(
        //@CreatedDate
        private val createdDate: Instant = Instant.now(),

        @CreatedBy
        private var createdBy: AggregateId? = null,

        @LastModifiedDate
        private var lastModifiedDate: Instant? = null,

        @LastModifiedBy
        private var lastModifiedBy: AggregateId? = null
) : Auditable<AggregateId, Instant>, IdentifiedEntity<EntityIdType> {

    override fun getCreatedDate() = createdDate

    override fun getCreatedBy() = createdBy

    override fun getLastModifiedDate() = lastModifiedDate

    override fun getLastModifiedBy() = lastModifiedBy
}
