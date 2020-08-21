package org.bialydunajec.application.eventsourcing

interface DomainMessage {

    val domainMessageId: DomainMessageId

    val correlationId: CorrelationId?

    val causationId: CausationId?

}
