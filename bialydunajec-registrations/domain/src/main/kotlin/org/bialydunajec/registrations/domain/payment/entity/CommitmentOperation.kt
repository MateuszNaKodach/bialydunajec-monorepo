package org.bialydunajec.registrations.domain.payment.entity

import org.bialydunajec.ddd.domain.base.persistence.AuditableEntity
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.domain.payment.valueobject.OperationType
import javax.persistence.*

@Entity
@Table(schema = "camp_registrations")
internal class CommitmentOperation(
        @Enumerated(EnumType.STRING)
        val type: OperationType,
        @Embedded
        val amount: Money,
        val description: String?
) : AuditableEntity<CommitmentOperationId>() {

    @EmbeddedId
    override val entityId: CommitmentOperationId = CommitmentOperationId()
}