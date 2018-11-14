package org.bialydunajec.email.domain.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.EmailMessageLogId

class EmailMessage(
        val recipient: EmailAddress,
        val subject: String,
        val content: String,
        val emailMessageLogId: EmailMessageLogId? = null
)