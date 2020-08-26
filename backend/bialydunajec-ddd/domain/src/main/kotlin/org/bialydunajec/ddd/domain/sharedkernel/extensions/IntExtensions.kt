package org.bialydunajec.ddd.domain.sharedkernel.extensions

fun Int.isEven() = this.rem(2) == 0
fun Int.isOdd() = !this.isEven()
