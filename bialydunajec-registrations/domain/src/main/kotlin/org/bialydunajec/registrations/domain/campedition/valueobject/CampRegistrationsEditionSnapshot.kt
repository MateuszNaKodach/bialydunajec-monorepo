package org.bialydunajec.registrations.domain.campedition.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import java.time.LocalDate

data class CampRegistrationsEditionSnapshot(
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val editionStartDate: LocalDate,
        val editionEndDate: LocalDate,
        val editionPrice: Money,
        val editionDownPaymentAmount: Money?,
        val campRegistrations: CampRegistrationsSnapshot
)