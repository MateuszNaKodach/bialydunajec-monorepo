package org.bialydunajec.ddd.application.base.external.event

interface ExternalEventListener {

    fun handleExternalEvent(externalEvent: ExternalEvent<*>)
}