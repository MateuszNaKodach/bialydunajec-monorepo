package org.bialydunajec.ddd.application.base.external.command

interface ExternalCommandListener<ExternalCommandPayloadType, ExternalCommandType : ExternalCommand<ExternalCommandType>> {

    fun handleExternalCommand(externalCommand: ExternalCommandType)
}