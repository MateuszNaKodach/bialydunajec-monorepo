package org.bialydunajec.application.eventsourcing

import org.bialydunajec.eventsourcing.domain.DomainCommand

internal class DomainCommandMessage<DomainCommandType : DomainCommand<*>> private constructor(
        val domainCommand: DomainCommandType,
        val targetAggregateVersion: TargetAggregateVersion,
        val correlationId: CorrelationId,
        val causationId: CausationId,
        val metaData: MetaData
) {

    companion object {
        fun <DomainCommandType : DomainCommand<*>> withEvent(domainEvent: DomainCommandType) =
                Builder<DomainCommandType>(domainEvent)
    }

    interface NeedTargetAggregateVersion<DomainCommandType : DomainCommand<*>> {
        fun toTargetAggregateVersion(targetAggregateVersion: TargetAggregateVersion.Exactly): OptionalCausedByOrMetadata<DomainCommandType>
        fun toAnyAggregateVersion(): OptionalCausedByOrMetadata<DomainCommandType>
    }

    interface OptionalCausedByOrMetadata<DomainCommandType : DomainCommand<*>> {
        fun causedBy(message: DomainCommandMessage<*>): OptionalCausedByOrMetadata<DomainCommandType>
        fun causedBy(message: DomainEventMessage<*>): OptionalCausedByOrMetadata<DomainCommandType>
        fun withMetaData(key: String, value: Any?): OptionalCausedByOrMetadata<DomainCommandType>
        fun build(): DomainCommandMessage<DomainCommandType>
    }

    class Builder<DomainCommandType : DomainCommand<*>>(private val domainCommand: DomainCommandType) :
            NeedTargetAggregateVersion<DomainCommandType>,
            OptionalCausedByOrMetadata<DomainCommandType> {

        private lateinit var targetAggregateVersion: TargetAggregateVersion
        private var correlationId: CorrelationId? = null
        private var causationId: CausationId? = null
        private lateinit var metadata: MutableList<Pair<String, Any?>>

        override fun toTargetAggregateVersion(targetAggregateVersion: TargetAggregateVersion.Exactly): OptionalCausedByOrMetadata<DomainCommandType> =
                apply {
                    this.targetAggregateVersion = targetAggregateVersion
                }

        override fun toAnyAggregateVersion(): OptionalCausedByOrMetadata<DomainCommandType> =
                apply {
                    this.targetAggregateVersion = TargetAggregateVersion.Any
                }

        override fun causedBy(message: DomainEventMessage<*>): OptionalCausedByOrMetadata<DomainCommandType> =
                apply {
                    correlationId = message.correlationId
                    causationId = CausationId.from(message.domainEvent.domainEventId)
                }

        override fun causedBy(message: DomainCommandMessage<*>): OptionalCausedByOrMetadata<DomainCommandType> =
                apply {
                    correlationId = message.correlationId
                    causationId = CausationId.from(message.domainCommand.domainCommandId)
                }

        override fun withMetaData(key: String, value: Any?): OptionalCausedByOrMetadata<DomainCommandType> =
                apply {
                    metadata.add(Pair(key, value))
                }

        override fun build(): DomainCommandMessage<DomainCommandType> =
                DomainCommandMessage(
                        domainCommand,
                        targetAggregateVersion,
                        correlationId ?: CorrelationId.from(domainCommand.domainCommandId),
                        causationId ?: CausationId.from(domainCommand.domainCommandId),
                        MetaData.from(*metadata.toTypedArray())
                )
    }

}
