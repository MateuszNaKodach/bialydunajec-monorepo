package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId

class EmailMessageLogId(trackingCode: String? = null) : AggregateId(trackingCode ?: defaultValue())