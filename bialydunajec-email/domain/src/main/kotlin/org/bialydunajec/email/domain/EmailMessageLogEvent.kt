package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.event.DomainEvent

sealed class EmailMessageLogEvent : DomainEvent<EmailMessageLogId> {
}