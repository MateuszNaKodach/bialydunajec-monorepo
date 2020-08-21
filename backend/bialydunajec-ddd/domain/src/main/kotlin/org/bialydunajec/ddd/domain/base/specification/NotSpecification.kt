package org.bialydunajec.ddd.domain.base.specification

class NotSpecification<T>(private val wrapped: Specification<T>) : CompositeSpecification<T>() {

    override fun isSatisfiedBy(candidate: T): Boolean {
        return !wrapped.isSatisfiedBy(candidate)
    }
}