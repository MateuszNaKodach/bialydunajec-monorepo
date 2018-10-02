package org.bialydunajec.ddd.domain.base.validation.exception

interface DomainRule {
    fun getName(): String
    fun getDescription(): String
}