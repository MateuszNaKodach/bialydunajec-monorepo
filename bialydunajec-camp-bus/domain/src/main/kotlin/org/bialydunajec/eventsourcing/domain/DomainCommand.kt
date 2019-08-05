package org.bialydunajec.eventsourcing.domain

abstract class DomainCommand<AggregateIdType : AggregateId>(

        /**
         * Field providing the identifier of the aggregate that a command targets.
         */
        val aggregateId: AggregateIdType,

        val domainCommandId: DomainCommandId = DomainCommandId()
)
