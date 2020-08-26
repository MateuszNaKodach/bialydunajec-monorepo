package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.extensions.toStringOrNull


interface ValueObject
fun ValueObject?.toString(): String? = this?.toStringOrNull()
