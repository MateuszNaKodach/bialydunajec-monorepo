package org.bialydunajec.ddd.application.base.external.event

//TODO: Add logic for recording event to internal storage
interface SpringExternalEventListener {

    fun handleExternalEvent(externalEvent: ExternalEvent<*>)
}
