package org.bialydunajec.users.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class UserAccountEvent : DomainEvent<UserAccountId> {

}