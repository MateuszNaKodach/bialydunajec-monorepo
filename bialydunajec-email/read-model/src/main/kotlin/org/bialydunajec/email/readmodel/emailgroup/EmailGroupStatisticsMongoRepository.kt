package org.bialydunajec.email.readmodel.emailgroup

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import javax.annotation.PostConstruct

interface EmailGroupStatisticsMongoRepository : MongoRepository<EmailGroupStatistics, String>

@Configuration
class BootEmailGroup(val repository: EmailGroupStatisticsMongoRepository) {

    @PostConstruct
    fun setUp() {
        repository.save(EmailGroupStatistics())
    }
}
