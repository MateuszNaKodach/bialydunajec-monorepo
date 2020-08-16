package org.bialydunajec.eventsourcing.domain

import java.time.Instant

typealias TimeProvider = () -> Instant