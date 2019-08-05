package org.bialydunajec.eventsourcing.infrastructure.eventstore.embedded.engine


class EventStreamVersionIsNotAsExpected(expected: ExpectedEventStreamVersion, actual: EventStreamVersion)
    : RuntimeException("Invalid aggregate aggregateVersion! Actual: <$actual>, expected: <$expected>")
