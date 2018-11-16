package org.bialydunajec.registrations.domain.payment.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.payment.entity.PaymentCommitmentId
import java.time.Instant
import java.time.ZonedDateTime

data class PaymentCommitmentSnapshot(
        val paymentCommitmentId: PaymentCommitmentId,
        val initialAmount: Money,
        val description: String?,
        val deadlineDate: Instant?,
        val amount: Money,
        val paid: Boolean
)