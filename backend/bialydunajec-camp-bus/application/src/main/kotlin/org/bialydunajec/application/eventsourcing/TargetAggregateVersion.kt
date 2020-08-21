package org.bialydunajec.application.eventsourcing

internal sealed class TargetAggregateVersion {

    object Any : TargetAggregateVersion()

    data class Exactly(val version: Long) : TargetAggregateVersion() {
        fun toLong() = version
    }

}
