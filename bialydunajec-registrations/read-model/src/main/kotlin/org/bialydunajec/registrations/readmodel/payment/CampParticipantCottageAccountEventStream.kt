package org.bialydunajec.registrations.readmodel.payment

import org.bialydunajec.ddd.application.base.external.event.ObservableEventStream
import org.springframework.stereotype.Component

@Component
internal class CampParticipantCottageAccountEventStream : ObservableEventStream()