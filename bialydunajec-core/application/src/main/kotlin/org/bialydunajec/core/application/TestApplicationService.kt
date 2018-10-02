package org.bialydunajec.core.application

import org.bialydunajec.core.domain.TestDomainRepository
import org.springframework.stereotype.Service

@Service
class TestApplicationService(val testDomainRepository: TestDomainRepository) {

    fun testApplication() = "Test Application Service";

    fun testDomain() = testDomainRepository.getTestAggregateRoot().getTestEntity()
}