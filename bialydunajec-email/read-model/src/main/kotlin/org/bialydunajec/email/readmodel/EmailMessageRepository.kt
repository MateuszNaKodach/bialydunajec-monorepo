package org.bialydunajec.email.readmodel

import org.springframework.data.mongodb.repository.MongoRepository

interface EmailMessageRepository : MongoRepository<EmailMessage, String>
