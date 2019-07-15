package org.bialydunajec.eventsourcing.domain

class AggregateVersionMismatchException(val actual: AggregateVersion, val expected: AggregateVersion)
    : IllegalStateException("Invalid aggregate version! Actual: <$actual>, expected: <$expected>")