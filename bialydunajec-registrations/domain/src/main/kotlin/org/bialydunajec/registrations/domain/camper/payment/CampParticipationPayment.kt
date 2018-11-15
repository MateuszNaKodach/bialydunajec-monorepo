package org.bialydunajec.registrations.domain.camper.payment

import org.bialydunajec.ddd.domain.base.aggregate.AuditableAggregateRoot
import org.bialydunajec.ddd.domain.base.persistence.Versioned
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.payment.PaymentCommitmentId
import javax.persistence.*

// TODO: PaymentCommitment bedzie tworzone i przypisywane id po potwierdzeniu uczestnictwa przez emial. Przemyslec czy to dobrze pokazuje model
@Entity
@Table(schema = "camp_registrations") //TODO: TO jest platnosc za udzial w Obozie z konkretna chatka!!! Jesli zmieni sie chatke to anuluje sie platnosc!
class CampParticipationPayment internal constructor(
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "campParticipantId")))
        val campParticipantId: CampParticipantId,
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        val cottageId: CottageId,
        @Embedded
        val initialAmount: Money,
        val description: String? = null,
        @Embedded
        var paymentCommitmentId: PaymentCommitmentId? = null
) : AuditableAggregateRoot<CampParticipationPaymentId, CampParticipationPaymentEvent>(CampParticipationPaymentId()), Versioned {
    @Version
    private var version: Long? = null
    var paid: Boolean = false
    override fun getVersion() = version
}