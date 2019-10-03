package org.bialydunajec.email.readmodel.groupwithemails

import org.bialydunajec.email.readmodel.shared.EmailOwner
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("groupWithEmailsReadModel")
data class GroupWithEmails(
    @Id
    var emailGroupId: String,
    var groupName: String? = null,
    var emails: MutableSet<Email> = mutableSetOf()
) {
    data class Email(val emailId: String, val address: String, var owner: EmailOwner)

    val size: Int
        get() = emails.size
}
