package org.bialydunajec.email.readmodel.emailmessage

import org.springframework.data.mongodb.repository.MongoRepository

internal interface EmailMessageMongoRepository : MongoRepository<EmailMessage, String>{
}
