package org.bialydunajec.registrations.domain.payment.valueobject

import javax.persistence.Embeddable

@Embeddable
internal enum class AccountStatus {
    INACTIVE,
    ACTIVE;
}