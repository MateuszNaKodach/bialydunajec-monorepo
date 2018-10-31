package org.bialydunajec.registrations.domain.camper.payment

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.payment.PaymentCommitmentId
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Version

// TODO: PaymentCommitment bedzie tworzone i przypisywane id po potwierdzeniu uczestnictwa przez emial. Przemyslec czy to dobrze pokazuje model
@Entity
@Table(schema = "camp_registrations")
class CampParticipationPayment internal constructor(
        val campParticipantId: CampParticipantId,
        val cottageId: CottageId,
        val amount: Money,
        val description: String? = null,
        var paymentCommitmentId: PaymentCommitmentId? = null
) : AuditableAggregateRoot<CampParticipationPaymentId, CampParticipationPaymentEvent>(CampParticipationPaymentId()), Versioned {
    @Version
    private var version: Long? = null
    var paid: Boolean = false
    override fun getVersion() = version
}