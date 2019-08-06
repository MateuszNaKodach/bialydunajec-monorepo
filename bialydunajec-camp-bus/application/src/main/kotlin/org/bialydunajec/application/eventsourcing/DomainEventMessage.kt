package org.bialydunajec.application.eventsourcing

import org.bialydunajec.eventsourcing.domain.DomainEvent

internal class DomainEventMessage<DomainEventType : DomainEvent<*>> private constructor(
        val domainEvent: DomainEventType,
        val targetAggregateVersion: TargetAggregateVersion,
        val correlationId: CorrelationId,
        val causationId: CausationId,
        val metaData: MetaData
) {

    companion object {
        fun <DomainEventType : DomainEvent<*>> withEvent(domainEvent: DomainEventType) =
                Builder<DomainEventType>(domainEvent)
    }

    interface NeedTargetAggregateVersion<DomainEventType : DomainEvent<*>> {
        fun toTargetAggregateVersion(targetAggregateVersion: TargetAggregateVersion.Exactly): RequireCausedBy<DomainEventType>
        fun toAnyAggregateVersion(): RequireCausedBy<DomainEventType>
    }

    interface RequireCausedBy<DomainEventType : DomainEvent<*>> {
        fun causedBy(message: DomainEventMessage<*>): OptionalMetaData<DomainEventType>
        fun causedBy(message: DomainCommandMessage<*>): OptionalMetaData<DomainEventType>
    }

    interface OptionalMetaData<DomainEventType : DomainEvent<*>> {
        fun withMetaData(key: String, value: Any?): OptionalMetaData<DomainEventType>
        fun build(): DomainEventMessage<DomainEventType>
    }

    class Builder<DomainEventType : DomainEvent<*>>(private val domainEvent: DomainEventType) :
            NeedTargetAggregateVersion<DomainEventType>,
            RequireCausedBy<DomainEventType>, OptionalMetaData<DomainEventType> {

        private lateinit var targetAggregateVersion: TargetAggregateVersion
        private lateinit var correlationId: CorrelationId
        private lateinit var causationId: CausationId
        private lateinit var metadata: MutableList<Pair<String, Any?>>

        override fun toTargetAggregateVersion(targetAggregateVersion: TargetAggregateVersion.Exactly): RequireCausedBy<DomainEventType> =
                apply {
                    this.targetAggregateVersion = targetAggregateVersion
                }

        override fun toAnyAggregateVersion(): RequireCausedBy<DomainEventType> =
                apply {
                    this.targetAggregateVersion = TargetAggregateVersion.Any
                }

        override fun causedBy(message: DomainEventMessage<*>): OptionalMetaData<DomainEventType> =
                apply {
                    correlationId = message.correlationId
                    causationId = CausationId.from(message.domainEvent.domainEventId)
                }

        override fun causedBy(message: DomainCommandMessage<*>): OptionalMetaData<DomainEventType> =
                apply {
                    correlationId = message.correlationId
                    causationId = CausationId.from(message.domainCommand.domainCommandId)
                }

        override fun withMetaData(key: String, value: Any?): OptionalMetaData<DomainEventType> =
                apply {
                    metadata.add(Pair(key, value))
                }

        override fun build(): DomainEventMessage<DomainEventType> =
                DomainEventMessage(domainEvent, targetAggregateVersion, correlationId, causationId, MetaData.from(*metadata.toTypedArray()))
    }

}
