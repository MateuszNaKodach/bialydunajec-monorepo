package org.bialydunajec.ddd.domain.base.specification

class DisjunctionSpecification<T>(private vararg val disjunction: Specification<T>) : CompositeSpecification<T>() {

    override fun isSatisfiedBy(candidate: T): Boolean {
        for (spec in disjunction) {
            if (spec.isSatisfiedBy(candidate))
                return true
        }

        return false
    }
}