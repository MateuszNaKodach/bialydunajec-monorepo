package org.bialydunajec.email.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
data class EmailMessage(
        @Id
        var emailMessageLogId: String,
        var recipient: String? = null,
        var subject: String? = null,
        var content: String? = null,
        var status: String? = null,
        var lastError: String? = null,
        var sentDate: String? = null,
        var createdDate: String? = null
)