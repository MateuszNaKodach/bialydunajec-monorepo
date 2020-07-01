package org.bialydunajec.application.eventsourcing

import org.bialydunajec.eventsourcing.domain.AggregateId
import org.bialydunajec.eventsourcing.domain.DomainCommand
import org.bialydunajec.eventsourcing.domain.DomainEvent
import org.bialydunajec.eventsourcing.domain.EventSourcedAggregateRoot

internal abstract class GenericApplicationService<AggregateIdType : AggregateId,
        AggregateCommandType : DomainCommand<AggregateIdType>,
        AggregateEventType : DomainEvent<AggregateIdType>,
        AggregateRootType : EventSourcedAggregateRoot<AggregateIdType, AggregateCommandType, AggregateEventType, AggregateRootType>> {


    fun process(aggregate: AggregateRootType, command: AggregateCommandType) =
            aggregate.process(command)

    /*
    fun execute
        która bierze Message z Command i przetwarza w domain command, jakieś metadane, correlation id itp.
        i zwraca Message z DomainEvent i przetwarza odpowiednio.
        Chyba nie ma po co innych command wproawdzać niż domain!
        Co z repository? Wtedy nie jest transparentne na EventSourcing!?
        Add auditing for events - which user!?
        Check authorization/ authentication
     */

}