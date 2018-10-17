package org.bialydunajec.ddd.application.base.email

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress

class EmailMessage(
        val recipients: Set<EmailAddress>,
        val subject: String,
        val content: String
) {
    constructor(recipient: EmailAddress, subject: String, content: String) : this(setOf(recipient), subject, content)
}