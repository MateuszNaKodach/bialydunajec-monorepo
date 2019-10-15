package org.bialydunajec.email.infrastructure

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableAutoConfiguration
@EnableJpaRepositories("org.bialydunajec.email")
@EntityScan(basePackages = ["org.bialydunajec.email"])
@Configuration
internal class DataJpaTestConfiguration {
}
