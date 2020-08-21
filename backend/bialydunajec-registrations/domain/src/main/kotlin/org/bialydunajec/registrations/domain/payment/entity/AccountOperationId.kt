package org.bialydunajec.registrations.domain.payment.entity

import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import javax.persistence.Embeddable

@Embeddable
class AccountOperationId : EntityId()