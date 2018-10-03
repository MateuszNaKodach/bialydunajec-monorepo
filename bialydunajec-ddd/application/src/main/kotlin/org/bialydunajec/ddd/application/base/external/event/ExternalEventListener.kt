package org.bialydunajec.ddd.application.base.external.event

interface ExternalEventListener<ExternalEventPayloadType, ExternalEventType: ExternalEvent<ExternalEventPayloadType>> {

    fun handleExternalEvent(externalEvent: ExternalEventType)
}