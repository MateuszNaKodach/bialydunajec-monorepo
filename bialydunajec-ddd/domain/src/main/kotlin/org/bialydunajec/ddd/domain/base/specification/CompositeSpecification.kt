package org.bialydunajec.ddd.domain.base.specification

import java.util.*

abstract class CompositeSpecification<T> : Specification<T> {

    override fun and(other: Specification<T>): Specification<T> {
        return AndSpecification<T>(this, other)
    }

    override fun or(other: Specification<T>): Specification<T> {
        return OrSpecification<T>(this, other)
    }

    override fun not(): Specification<T> {
        return NotSpecification<T>(this)
    }

    override fun conjunction(vararg others: Specification<T>): Specification<T> {
        val list = others.toMutableList()
        list.add(this)
        return Conjunction<T>(list)
    }
}