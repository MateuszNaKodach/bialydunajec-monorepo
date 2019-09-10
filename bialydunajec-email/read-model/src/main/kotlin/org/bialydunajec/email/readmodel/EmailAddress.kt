package org.bialydunajec.email.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("emailAddressReadModel")
data class EmailAddress(
        @Id
        var emailAddressId: String,
        var emailAddress: String? = null,
        var ownerFirstName: String? = null,
        var ownerLastName: String? = null,
        var emailGroupIds: List<String>? = mutableListOf()
)