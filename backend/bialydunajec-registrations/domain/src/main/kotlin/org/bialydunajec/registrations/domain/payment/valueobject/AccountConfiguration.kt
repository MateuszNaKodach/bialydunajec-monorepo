package org.bialydunajec.registrations.domain.payment.valueobject

import javax.persistence.Embeddable

@Embeddable
data class AccountConfiguration(
        val depositEnabled: Boolean = true,
        val withdrawEnabled: Boolean = true//,
        //val paymentEnabled: Boolean = true, removePaymentEnabled
)