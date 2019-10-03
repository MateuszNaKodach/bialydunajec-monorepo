package org.bialydunajec.email.readmodel.groupwithemails

import org.springframework.data.mongodb.repository.MongoRepository

internal interface GroupWithEmailsMongoRepository : MongoRepository<GroupWithEmails, String>
