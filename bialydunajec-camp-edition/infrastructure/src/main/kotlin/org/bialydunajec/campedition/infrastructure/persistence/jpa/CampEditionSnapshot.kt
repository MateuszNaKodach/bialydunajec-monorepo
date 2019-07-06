package org.bialydunajec.campedition.infrastructure.persistence.jpa

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import java.time.LocalDate

data class CampEditionSnapshot(
        val campEditionId: CampEditionId,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val price: Money,
        val downPaymentAmount: Money?
)
