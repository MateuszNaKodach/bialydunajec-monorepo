package org.bialydunajec.core.domain

interface TestDomainRepository {

    fun getTestAggregateRoot():TestAggregateRoot
}