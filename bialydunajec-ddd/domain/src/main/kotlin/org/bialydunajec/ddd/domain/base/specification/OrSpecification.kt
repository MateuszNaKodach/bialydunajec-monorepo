package org.bialydunajec.ddd.domain.base.specification

class OrSpecification<T>(private val a: Specification<T>, private val b: Specification<T>) : CompositeSpecification<T>() {

    override fun isSatisfiedBy(candidate: T): Boolean {
        return a.isSatisfiedBy(candidate) || b.isSatisfiedBy(candidate)
    }
}