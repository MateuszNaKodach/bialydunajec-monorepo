package org.bialydunajec.email.messages.command

sealed class EmailMessageExternalCommand {
    data class SendSimpleEmailMessage(
            val recipientEmailAddress: String,
            val subject: String,
            val content: String,
            val trackingCode: String? = null
    ) : EmailMessageExternalCommand()
}