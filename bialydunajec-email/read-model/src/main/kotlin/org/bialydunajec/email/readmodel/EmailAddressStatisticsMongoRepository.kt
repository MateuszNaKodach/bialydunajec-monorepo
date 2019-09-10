package org.bialydunajec.email.readmodel

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import javax.annotation.PostConstruct

interface EmailAddressStatisticsMongoRepository : MongoRepository<EmailAddressStatistics, String>

@Configuration
class BootEmailAddress(val repository: EmailAddressStatisticsMongoRepository) {

    @PostConstruct
    fun setUp() {
        repository.save(EmailAddressStatistics())
    }
}