package org.bialydunajec.registrations.readmodel.shirt

import org.bialydunajec.ddd.application.base.external.event.ObservableEventStream
import org.springframework.stereotype.Component

@Component
internal class ShirtOrderEventStream : ObservableEventStream()