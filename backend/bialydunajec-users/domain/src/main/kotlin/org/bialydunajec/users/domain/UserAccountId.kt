package org.bialydunajec.users.domain

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId

class UserAccountId(userAccountId: String): AggregateId(userAccountId)
