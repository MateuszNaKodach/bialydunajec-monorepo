package org.bialydunajec.ddd.domain.base.specification

/*
Specification in the sense of Domain Driven Design.
 */
interface Specification<T> {
    fun isSatisfiedBy(candidate: T): Boolean

    fun and(other: Specification<T>): Specification<T>

    fun or(other: Specification<T>): Specification<T>

    fun conjunction(vararg others: Specification<T>): Specification<T>

    operator fun not(): Specification<T>
}