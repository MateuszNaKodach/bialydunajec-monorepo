package org.bialydunajec.registrations.domain.campedition.valueobject

import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrationsId
import java.time.ZonedDateTime

data class CampRegistrationsSnapshot(
        val campRegistrationsId: CampRegistrationsId,
        val status: RegistrationsStatus,
        val timerSettings: TimerSettings,
        val lastStartedAt: ZonedDateTime?,
        val lastSuspendAt: ZonedDateTime?,
        val lastUnsuspendAt: ZonedDateTime?,
        val lastFinishedAt: ZonedDateTime?
)