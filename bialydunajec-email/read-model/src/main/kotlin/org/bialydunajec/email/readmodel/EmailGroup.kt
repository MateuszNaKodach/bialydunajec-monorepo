package org.bialydunajec.email.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("emailGroupReadModel")
data class EmailGroup(
    @Id
    var emailGroupId: String,
    var groupName: String? = null,
    var emailAddresses: MutableSet<String> = mutableSetOf() //TODO: Tutaj coś nie tak, chcemy wyświetlać dane kto to posiada?
)
