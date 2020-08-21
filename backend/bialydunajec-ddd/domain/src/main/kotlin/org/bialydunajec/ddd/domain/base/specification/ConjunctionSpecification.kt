package org.bialydunajec.ddd.domain.base.specification

class ConjunctionSpecification<T>(private vararg val conjunction: Specification<T>) : CompositeSpecification<T>() {

    override fun isSatisfiedBy(candidate: T): Boolean {
        for (spec in conjunction) {
            if (!spec.isSatisfiedBy(candidate))
                return false
        }

        return true
    }
}