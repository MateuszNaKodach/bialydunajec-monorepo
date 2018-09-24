package org.bialydunajec.backend

import org.bialydunajec.core.presentation.TestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test-service")
class TestServiceController(val testService: TestService) {

    @GetMapping
    fun test() = testService.helloWorld()

}