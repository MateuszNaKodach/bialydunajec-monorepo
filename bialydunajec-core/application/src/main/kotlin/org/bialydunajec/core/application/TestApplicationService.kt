package org.bialydunajec.core.application

import org.bialydunajec.core.domain.TestAggregateRoot
import org.springframework.stereotype.Service

@Service
class TestApplicationService {

    fun testApplication() = "Test Application Service";

    fun testDomain() = TestAggregateRoot().getTestEntity()
}