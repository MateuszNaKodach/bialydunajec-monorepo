package org.bialydunajec.registrations.domain

interface TestDomainRepository {

    fun getTestAggregateRoot():TestCampersRegisterAggregateRoot
}