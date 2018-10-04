package org.bialydunajec.registrations.domain.campedition.valueobject

import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import java.time.LocalDate

data class CampRegistrationsEditionSnapshot(
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val editionStartDate: LocalDate,
        val editionEndDate: LocalDate,
        val campRegistrations: CampRegistrationsSnapshot
)