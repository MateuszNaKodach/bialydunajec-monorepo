package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import java.time.LocalDate

internal class CampEdition(
        private val campEditionId: CampEditionId,
        private val startDate: LocalDate,
        private val endDate: LocalDate,
        private val totalPrice: Money,
        private val downPaymentAmount: Money? = null
){

    private val changes = listOf<>()
}
