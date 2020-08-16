package org.bialydunajec.registrations.readmodel.camper

import org.bialydunajec.ddd.application.base.external.event.ObservableEventStream
import org.springframework.stereotype.Component

@Component
internal class CampParticipantEventStream : ObservableEventStream()