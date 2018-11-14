package org.bialydunajec.ddd.application.base.external.command


interface ExternalCommandListener {

    fun handleExternalCommand(externalCommand: ExternalCommand<*>)
}