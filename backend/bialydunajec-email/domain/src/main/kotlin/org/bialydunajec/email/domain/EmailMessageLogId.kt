package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId

class EmailMessageLogId(trackingCode: String? = null) : AggregateId(trackingCode ?: defaultValue())
