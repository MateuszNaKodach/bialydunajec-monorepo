package org.bialydunajec.registrations.domain.payment

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.camper.payment.CampParticipationPayment
import org.bialydunajec.registrations.domain.payment.entity.CommitmentOperation
import org.bialydunajec.registrations.domain.payment.valueobject.OperationType
import java.time.ZonedDateTime
import javax.persistence.*

//TODO: Create payments bounded context!!!
@Entity
@Table(schema = "camp_registrations")
class PaymentCommitment(
        @Embedded
        var amount: Money,
        var title: String,
        var description: String? = null,
        var deadlineDate: ZonedDateTime? = null
) : AuditableAggregateRoot<PaymentCommitmentId, PaymentCommitmentEvent>(PaymentCommitmentId()), Versioned {
    @Version
    private var version: Long? = null

    private var cancelled: Boolean = false

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private var operations: MutableSet<CommitmentOperation> = mutableSetOf()

    override fun getVersion() = version

    fun pay(amount: Money, description: String? = null) {
        operations.add(CommitmentOperation(OperationType.PAYMENT, amount, description))
    }

    fun withdraw(amount: Money, description: String? = null) {
        operations.add(CommitmentOperation(OperationType.WITHDRAW, amount, description))
    }

    fun getBalance() =
            operations.fold(Money.zero()) { acc, commitmentOperation ->
                when (commitmentOperation.type) {
                    OperationType.PAYMENT -> acc.add(commitmentOperation.amount)
                    OperationType.WITHDRAW -> acc.subtract(commitmentOperation.amount)
                }
            }

    fun isPaid() =
            getBalance().greaterOrEquals(amount)

    fun cancel() {
        cancelled = true
    }

    fun restore() {
        cancelled = true
    }

    companion object {
        fun createFrom(payment: CampParticipationPayment) =
                with(payment) {
                    PaymentCommitment(
                            amount,
                            "Opłata za Obóz",
                            description
                    )
                }

    }

}