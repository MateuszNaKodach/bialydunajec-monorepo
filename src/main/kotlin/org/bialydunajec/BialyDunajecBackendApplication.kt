package org.bialydunajec

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableAsync


@SpringBootApplication
@EnableSpringDataWebSupport
@EnableAsync
class BialyDunajecBackendApplication


fun main(args: Array<String>) {
    runApplication<BialyDunajecBackendApplication>(*args)
}