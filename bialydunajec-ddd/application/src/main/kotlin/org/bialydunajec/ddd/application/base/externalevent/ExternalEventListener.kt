package org.bialydunajec.ddd.application.base.externalevent

interface ExternalEventListener<ExternalEventType: ExternalEvent> {

    fun handleExternalEvent(externalEvent: ExternalEventType)
}