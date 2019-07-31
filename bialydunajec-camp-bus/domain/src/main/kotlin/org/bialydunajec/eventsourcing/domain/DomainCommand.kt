package org.bialydunajec.eventsourcing.domain

abstract class DomainCommand<AggregateIdType : AggregateId, DomainCommandType : DomainCommand<AggregateIdType, DomainCommandType>>(

        /**
         * Field providing the identifier of the aggregate that a command targets.
         */
        val aggregateId: AggregateId,

        /**
         * Field providing the version of the aggregate that a command targets.
         */
        val aggregateVersion: AggregateVersion,

        val domainCommandId: DomainCommandId = DomainCommandId()
) : DomainMessage {

    override val domainMessageId: DomainMessageId
        get() = DomainMessageId.from(domainCommandId)

    override val correlationId: CorrelationId
        get() = CorrelationId.from(domainCommandId)

    override val causationId: CausationId
        get() = CausationId.from(domainCommandId)

}
