package org.bialydunajec.eventsourcing.domain

class AggregateVersionMismatchException(val actual: AggregateVersion, val expected: AggregateVersion)
    : IllegalStateException("Invalid aggregate aggregateVersion! Actual: <$actual>, expected: <$expected>")
