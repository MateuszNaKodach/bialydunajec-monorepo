package org.bialydunajec.ddd.application.base.external.command

import org.bialydunajec.ddd.application.base.external.ExternalMessage

class ExternalCommand<PayloadType : Any>(payload: PayloadType) : ExternalMessage<PayloadType>(payload)
