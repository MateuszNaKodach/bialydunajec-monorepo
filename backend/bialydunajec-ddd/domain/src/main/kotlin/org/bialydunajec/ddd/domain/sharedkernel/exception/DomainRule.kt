package org.bialydunajec.ddd.domain.sharedkernel.exception

interface DomainRule {
    fun getRuleName(): String
    fun getDescription(): String?
}
