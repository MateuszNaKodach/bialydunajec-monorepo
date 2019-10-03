package org.bialydunajec.email.readmodel.groupwithemails

import org.bialydunajec.email.readmodel.shared.EmailOwner
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("groupWithEmailsReadModel")
data class GroupWithEmails(
    @Id
    var emailGroupId: String,
    var groupName: String? = null,
    private var emails: MutableSet<Email> = mutableSetOf()
) {
    class Email(val emailId: String, val address: String, var owner: EmailOwner) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Email

            if (emailId != other.emailId) return false

            return true
        }

        override fun hashCode(): Int {
            return emailId.hashCode()
        }
    }

    val size: Int
        get() = emails.size

    val groupEmails: Set<Email>
        get() = setOf(*emails.toTypedArray())

    fun addOrUpdateEmail(email: Email) {
        if (emails.contains(email)) {
            emails.remove(email)
        }
        emails.add(email)
    }
}
