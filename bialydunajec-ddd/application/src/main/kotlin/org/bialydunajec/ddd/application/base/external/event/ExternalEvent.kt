package org.bialydunajec.ddd.application.base.external.event

import org.bialydunajec.ddd.application.base.external.ExternalMessage

class ExternalEvent<PayloadType>(payload: PayloadType) : ExternalMessage<PayloadType>(payload)