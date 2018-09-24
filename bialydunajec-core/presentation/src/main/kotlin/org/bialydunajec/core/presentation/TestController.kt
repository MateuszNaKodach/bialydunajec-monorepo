package org.bialydunajec.core.presentation

import org.bialydunajec.core.application.TestApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController(val testApplicationService: TestApplicationService) {

    @GetMapping("/test")
    fun test() = "Test response core"


    @GetMapping("/test-application")
    fun testApplication() = testApplicationService.testApplication()

    @GetMapping("/test-domain")
    fun testDomain() = testApplicationService.testDomain()
}