package org.bialydunajec.campedition.infrastructure.persistence.jpa

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents
import java.util.ArrayList
import java.util.Collections
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.MappedSuperclass

/**
 * Aggregate Root base class based in the Spring Data one {@link org.springframework.data.domain.AbstractAggregateRoot}.
 */
@MappedSuperclass
abstract class DbAggregateRoot<AggregateIdType : Identifier<*>, EventType : DomainEvent<AggregateIdType>>(
        @Column(unique = true, updatable = false)
        @EmbeddedId
        private val aggregateId: AggregateIdType
) {

    fun getAggregateId() = aggregateId

    override fun equals(other: Any?): Boolean = other != null && this.hashCode() == other.hashCode()

    override fun hashCode(): Int = aggregateId.hashCode()

}
