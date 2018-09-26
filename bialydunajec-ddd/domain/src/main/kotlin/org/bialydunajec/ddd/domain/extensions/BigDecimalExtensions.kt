package org.bialydunajec.ddd.domain.extensions

import java.math.BigDecimal

fun BigDecimal.isNegative() = this < BigDecimal.ZERO
fun BigDecimal.isZero() = this == BigDecimal.ZERO