package org.bialydunajec.eventsourcing.domain

class BrokenEventSequenceException(val actual: AggregateVersion, val expected: AggregateVersion)
    : IllegalStateException("Invalid aggregate aggregateVersion! Actual: <$actual>, expected: <$expected>")
