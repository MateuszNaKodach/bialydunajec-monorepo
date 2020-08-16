package org.bialydunajec.eventsourcing.domain

import java.time.Clock
fun Clock.toFixed() = Clock.fixed(instant(), zone)
