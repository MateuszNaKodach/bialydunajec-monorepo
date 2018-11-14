package org.bialydunajec.email.readmodel

import org.bialydunajec.email.readmodel.rest.EmailMessageStatistics
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.reactive.RxJava2CrudRepository
import javax.annotation.PostConstruct

interface EmailMessageStatisticsRepository : RxJava2CrudRepository<EmailMessageStatistics, Long>


@Configuration
class BootEmailMessageLog(val repository: EmailMessageStatisticsRepository) {


    @PostConstruct
    fun setUp() {
        repository.save(EmailMessageStatistics())
                .subscribe()
    }
}
