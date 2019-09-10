package org.bialydunajec.email.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("emailAddressCatalogGroupReadModel")
data class EmailAddressCatalogGroup(
    @Id
    var emailAGroupId: String,
    var groupName: String? = null,
    var emailAddressIds: List<String>? = mutableListOf()
)