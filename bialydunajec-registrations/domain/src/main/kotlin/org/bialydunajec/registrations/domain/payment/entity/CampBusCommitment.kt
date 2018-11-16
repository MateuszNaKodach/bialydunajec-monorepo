package org.bialydunajec.registrations.domain.payment.entity

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import java.time.Instant
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(schema = "camp_registrations")
internal class CampBusCommitment internal constructor(
        initialAmount: Money,
        description: String? = null,
        deadlineDate: Instant? = null
) : PaymentCommitment(initialAmount, description, deadlineDate)