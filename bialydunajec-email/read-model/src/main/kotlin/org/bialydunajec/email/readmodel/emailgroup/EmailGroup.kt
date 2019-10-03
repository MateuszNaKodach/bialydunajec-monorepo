package org.bialydunajec.email.readmodel.emailgroup

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

//GROUP_WITH_EMAILS
@Document("emailGroupReadModel")
data class EmailGroup(
    @Id
    var emailGroupId: String,
    var groupName: String? = null,
    var emailAddresses: MutableSet<String> = mutableSetOf() //TODO: Tutaj coś nie tak, chcemy wyświetlać dane kto to posiada?
)
