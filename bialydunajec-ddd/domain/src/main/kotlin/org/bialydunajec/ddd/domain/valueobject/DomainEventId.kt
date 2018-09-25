package org.bialydunajec.ddd.domain.valueobject

import java.util.*
import javax.persistence.Embeddable

@Embeddable
data class DomainEventId(val uuid: String = UUID.randomUUID().toString()) : Identifier