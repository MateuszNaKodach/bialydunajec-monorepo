package org.bialydunajec.email.readmodel.emailwithgroups

import org.bialydunajec.email.readmodel.shared.EmailOwner
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document("emailWithGroupsReadModel")
data class EmailWithGroups(
    @Id
    val address: String,
    var owner: EmailOwner,
    val groups: MutableSet<EmailGroup> = mutableSetOf()
) {
    data class EmailGroup(var emailGroupId: String, var groupName: String?)

    val id: String
        get() = address

    fun removeGroupById(emailGroupId: String) =
        groups.removeIf { it.emailGroupId == emailGroupId }

}
