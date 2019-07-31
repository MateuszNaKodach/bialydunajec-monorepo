package org.bialydunajec.eventsourcing.domain

interface DomainMessage {

    val domainMessageId: DomainMessageId

    val correlationId: CorrelationId?

    val causationId: CausationId?

}
