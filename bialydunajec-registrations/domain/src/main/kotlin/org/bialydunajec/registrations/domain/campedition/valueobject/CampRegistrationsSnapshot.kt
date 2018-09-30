package org.bialydunajec.registrations.domain.campedition.valueobject

import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrationsId
import java.time.ZonedDateTime

data class CampRegistrationsSnapshot(
        private val campRegistrationsId: CampRegistrationsId,
        private var timerSettings: TimerSettings,
        private var lastStartedAt: ZonedDateTime?,
        private var lastSuspendAt: ZonedDateTime?,
        private var lastUnsuspendAt: ZonedDateTime?,
        private var lastFinishedAt: ZonedDateTime?
)