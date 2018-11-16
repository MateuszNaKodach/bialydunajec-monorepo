package org.bialydunajec.email.readmodel

import org.springframework.data.mongodb.repository.MongoRepository

internal interface EmailMessageMongoRepository : MongoRepository<EmailMessage, String>{
    fun findByIdOrCreate(emailMessageLogId: String) = findById(emailMessageLogId)
            .orElseGet { EmailMessage(emailMessageLogId) }
}
