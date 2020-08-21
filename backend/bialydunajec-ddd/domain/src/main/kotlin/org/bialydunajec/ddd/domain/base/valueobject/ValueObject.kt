package org.bialydunajec.ddd.domain.base.valueobject

import org.bialydunajec.ddd.domain.extensions.toStringOrNull


interface ValueObject
fun ValueObject?.toString(): String? = this?.toStringOrNull()
