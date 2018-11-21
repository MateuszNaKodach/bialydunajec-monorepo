package org.bialydunajec.registrations.domain.payment.entity

import org.bialydunajec.ddd.domain.base.persistence.AuditableEntity
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.payment.valueobject.PaymentCommitmentSnapshot
import org.bialydunajec.registrations.domain.payment.valueobject.PaymentCommitmentType
import java.time.Instant
import java.time.ZonedDateTime
import javax.persistence.*

// TODO: PaymentCommitment bedzie tworzone i przypisywane id po potwierdzeniu uczestnictwa przez emial. Przemyslec czy to dobrze pokazuje model
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@MappedSuperclass //TODO: TO jest platnosc za udzial w Obozie z konkretna chatka!!! Jesli zmieni sie chatke to anuluje sie platnosc!
internal abstract class PaymentCommitment internal constructor(
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "value", column = Column(name = "initialAmount_value")),
                AttributeOverride(name = "currency", column = Column(name = "initialAmount_currency"))
        )
        private val initialAmount: Money,
        private val description: String?,
        private var deadlineDate: Instant?,
        private val type: PaymentCommitmentType
) : AuditableEntity<PaymentCommitmentId>(), Versioned {
    @EmbeddedId
    override val entityId: PaymentCommitmentId = PaymentCommitmentId()

    @Embedded
    @AttributeOverrides(
            AttributeOverride(name = "value", column = Column(name = "amount_value")),
            AttributeOverride(name = "currency", column = Column(name = "amount_currency"))
    )
    private var amount: Money = initialAmount

    private var paid: Boolean = false
    private var paidDate: Instant? = null

    @Version
    private var version: Long? = null

    internal fun getInitialAmount() = initialAmount
    internal fun getDescription() = description
    internal fun getDeadlineDate() = deadlineDate
    internal fun getType() = type
    fun getAmount() = amount
    fun isPaid() = paid
    fun isNotPaid() = !paid
    fun markAsPaid() {
        paid = true
        paidDate = Instant.now()
    }

    fun getPaidAmount() = if (paid) amount else Money.zero()
    fun getPaidDate() = paidDate

    override fun getVersion() = version

    fun getSnapshot() =
            PaymentCommitmentSnapshot(entityId, type, initialAmount, description, deadlineDate, amount, paid, paidDate)
}