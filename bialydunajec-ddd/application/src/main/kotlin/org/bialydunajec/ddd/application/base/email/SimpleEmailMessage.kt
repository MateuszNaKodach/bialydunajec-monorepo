package org.bialydunajec.ddd.application.base.email

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress

data class SimpleEmailMessage(
        val recipients: Set<EmailAddress>,
        val subject: String,
        val content: String,
        val trackingCode: String? = null
) {
    constructor(recipient: EmailAddress, subject: String, content: String, trackingCode: String? = null)
            : this(setOf(recipient), subject, content, trackingCode)
}