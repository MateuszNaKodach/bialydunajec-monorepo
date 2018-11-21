package org.bialydunajec.email.presentation.rest.v1.admin.request

internal class SendEmailMessageRequest(
        val emailAddresses: Array<String>,
        val subject: String,
        val content: String,
        val emailMessageLogId: String? = null
)