package org.bialydunajec.ddd.infrastructure.base.external.event

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent

//TODO: Add logic for recording event to internal storage
interface SpringExternalEventListener {

    fun handleExternalEvent(externalEvent: ExternalEvent<*>)
}
