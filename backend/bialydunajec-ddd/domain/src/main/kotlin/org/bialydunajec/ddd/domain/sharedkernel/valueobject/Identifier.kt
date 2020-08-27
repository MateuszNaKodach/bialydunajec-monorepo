package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import java.io.Serializable

interface Identifier<ValueType> : ValueObject, Serializable {
    fun getIdentifierValue():ValueType
}
