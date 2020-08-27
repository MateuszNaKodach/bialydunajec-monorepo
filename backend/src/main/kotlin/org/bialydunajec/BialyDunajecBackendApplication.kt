package org.bialydunajec

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableScheduling


@EnableScheduling
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableMongoRepositories(basePackages = ["org.bialydunajec.registrations.readmodel","org.bialydunajec.email.readmodel"])
@EnableReactiveMongoRepositories(basePackages = ["org.bialydunajec.email.readmodel"])
class BialyDunajecBackendApplication


fun main(args: Array<String>) {
    runApplication<BialyDunajecBackendApplication>(*args)
}
