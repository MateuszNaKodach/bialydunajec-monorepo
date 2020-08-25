package org.bialydunajec.ddd.domain.base.event

interface DomainEventsRecorder {
    val recorded: List<DomainEvent<*>>

    fun clearRecordedEvents()
}
