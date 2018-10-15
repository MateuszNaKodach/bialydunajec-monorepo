package org.bialydunajec.registrations.application.dto

import org.bialydunajec.registrations.domain.campedition.valueobject.CampRegistrationsEditionSnapshot
import org.bialydunajec.registrations.domain.campedition.valueobject.CampRegistrationsSnapshot
import java.time.LocalDate
import java.time.ZonedDateTime

data class CampRegistrationsEditionDto(
        val campRegistrationsEditionId: String,
        val editionStartDate: LocalDate,
        val editionEndDate: LocalDate,
        val campRegistrations: CampRegistrationsDto
) {
    companion object {
        fun from(
                snapshot: CampRegistrationsEditionSnapshot) =
                CampRegistrationsEditionDto(
                        campRegistrationsEditionId = snapshot.campRegistrationsEditionId.toString(),
                        editionStartDate = snapshot.editionStartDate,
                        editionEndDate = snapshot.editionEndDate,
                        campRegistrations = CampRegistrationsDto.from(snapshot.campRegistrations)
                )
    }
}

data class  CampRegistrationsDto(
        val campRegistrationsId: String,
        val status: String,
        val timerStartDate: ZonedDateTime?,
        val timerEndDate: ZonedDateTime?,
        val lastStartedAt: ZonedDateTime?,
        val lastSuspendAt: ZonedDateTime?,
        val lastUnsuspendAt: ZonedDateTime?,
        val lastFinishedAt: ZonedDateTime?
) {
    companion object {
        fun from(snapshot: CampRegistrationsSnapshot) =
                CampRegistrationsDto(
                        campRegistrationsId = snapshot.campRegistrationsId.toString(),
                        status = snapshot.status.name,
                        timerStartDate = snapshot.timerSettings?.startDate,
                        timerEndDate = snapshot.timerSettings?.endDate,
                        lastStartedAt = snapshot.lastStartedAt,
                        lastSuspendAt = snapshot.lastSuspendAt,
                        lastUnsuspendAt = snapshot.lastUnsuspendAt,
                        lastFinishedAt = snapshot.lastFinishedAt
                )
    }
}