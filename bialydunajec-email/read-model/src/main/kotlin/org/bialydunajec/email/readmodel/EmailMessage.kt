package org.bialydunajec.email.readmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
data class EmailMessage(
        @Id
        val emailMessageLogId: String,
        var recipient: String,
        var subject: String,
        var content: String,
        var status: String,
        var lastError: String? = null,
        var sentDate: String? = null
)