package org.bialydunajec.campedition.domain.campedition.valueobject

import org.bialydunajec.campedition.domain.campedition.CampEditionId
import java.time.LocalDate

data class CampEditionSnapshot(
        val campEditionId: CampEditionId,
        val startDate: LocalDate,
        val endDate: LocalDate
)