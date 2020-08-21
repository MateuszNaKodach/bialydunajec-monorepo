package org.bialydunajec.ddd.domain.extensions

fun Any?.toStringOrNull(): String? = if (this == "null") null else this?.toString()
