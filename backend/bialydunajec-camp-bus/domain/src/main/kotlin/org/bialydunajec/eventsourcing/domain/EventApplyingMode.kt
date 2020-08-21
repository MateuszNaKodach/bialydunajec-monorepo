package org.bialydunajec.eventsourcing.domain

enum class EventApplyingMode {
    APPLY_NEW_CHANGE,
    REPLAY_HISTORY;

    companion object {
        val DEFAULT: EventApplyingMode = APPLY_NEW_CHANGE
    }
}