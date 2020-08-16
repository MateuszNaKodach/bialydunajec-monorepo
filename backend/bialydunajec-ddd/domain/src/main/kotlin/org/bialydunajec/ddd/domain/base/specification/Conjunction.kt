package org.bialydunajec.ddd.domain.base.specification

class Conjunction<T>(private val list: List<Specification<T>>) : CompositeSpecification<T>() {

    override fun isSatisfiedBy(candidate: T): Boolean {
        for (spec in list) {
            if (!spec.isSatisfiedBy(candidate))
                return false
        }

        return true
    }

}