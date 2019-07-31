package org.bialydunajec.eventsourcing.domain

abstract class DomainCommand<AggregateIdType : AggregateId>(

        /**
         * Field providing the identifier of the aggregate that a command targets.
         */
        val aggregateId: AggregateIdType,

        /**
         * Field providing the version of the aggregate that a command targets.
         */
        val aggregateVersion: AggregateVersion,

        val domainCommandId: DomainCommandId = DomainCommandId()
)
