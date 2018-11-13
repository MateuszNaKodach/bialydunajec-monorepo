package org.bialydunajec.campedition.domain.campedition.valueobject

import org.bialydunajec.campedition.domain.campedition.CampEditionId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import java.time.LocalDate

data class CampEditionSnapshot(
        val campEditionId: CampEditionId,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val price: Money
)