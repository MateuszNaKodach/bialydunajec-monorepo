package org.bialydunajec.registrations.infrastructure.time

import org.bialydunajec.ddd.application.base.time.Clock
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class TimeProvider : Clock {
    override fun currentDateTime(): ZonedDateTime = ZonedDateTime.now()
}