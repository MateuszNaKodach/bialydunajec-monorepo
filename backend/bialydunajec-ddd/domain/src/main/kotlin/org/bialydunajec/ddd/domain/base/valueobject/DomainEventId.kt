package org.bialydunajec.ddd.domain.base.valueobject

import java.util.*
import javax.persistence.Embeddable

@Embeddable
data class DomainEventId(val uuid: String = UUID.randomUUID().toString()) : Identifier<String>{
    override fun getIdentifierValue() = uuid
}