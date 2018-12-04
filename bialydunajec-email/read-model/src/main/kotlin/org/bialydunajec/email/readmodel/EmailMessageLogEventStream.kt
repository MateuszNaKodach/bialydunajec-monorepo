package org.bialydunajec.email.readmodel

import org.bialydunajec.ddd.application.base.external.event.ObservableEventStream
import org.springframework.stereotype.Component

@Component
internal class EmailMessageLogEventStream : ObservableEventStream()