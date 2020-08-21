package org.bialydunajec.ddd.domain.base.validation.exception

interface DomainRule {
    fun getRuleName(): String
    fun getDescription(): String?
}