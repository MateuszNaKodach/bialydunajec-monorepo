package org.bialydunajec.registrations.readmodel.statistics

import org.bialydunajec.ddd.application.base.external.event.ObservableEventStream
import org.springframework.stereotype.Component

@Component
internal class CampRegistrationsEditionStatisticsEventStream : ObservableEventStream()