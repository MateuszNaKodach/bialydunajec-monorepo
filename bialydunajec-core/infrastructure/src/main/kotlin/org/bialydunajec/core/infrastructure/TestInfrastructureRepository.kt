package org.bialydunajec.core.infrastructure

import org.bialydunajec.core.domain.TestAggregateRoot
import org.bialydunajec.core.domain.TestDomainRepository
import org.springframework.stereotype.Repository

@Repository
class TestInfrastructureRepository : TestDomainRepository {

    override fun getTestAggregateRoot() = TestAggregateRoot()
}