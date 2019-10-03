package org.bialydunajec.email.readmodel.email

import org.bialydunajec.ddd.application.base.external.event.ObservableEventStream
import org.springframework.stereotype.Component

@Component
internal class EmailEventStream : ObservableEventStream()
