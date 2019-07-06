package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import java.util.*

data class DomainEventId(val uuid: String = UUID.randomUUID().toString()) : Identifier<String> {
    override fun getIdentifierValue() = uuid
}
