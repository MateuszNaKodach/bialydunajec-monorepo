package org.bialydunajec.ddd.application.base.external.event

interface ExternalEventListener<ExternalEventPayloadType> {

    fun handleExternalEvent(externalEvent: ExternalEvent<ExternalEventPayloadType>)
}