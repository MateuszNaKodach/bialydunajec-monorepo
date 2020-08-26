package org.bialydunajec.ddd.domain.sharedkernel.extensions

fun Any?.toStringOrNull(): String? = if (this == "null") null else this?.toString()
