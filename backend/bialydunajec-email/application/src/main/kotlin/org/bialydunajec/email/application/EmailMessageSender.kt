package org.bialydunajec.email.application

import org.bialydunajec.ddd.application.base.email.SimpleEmailMessage

interface EmailMessageSender {

    fun sendEmailMessage(message: SimpleEmailMessage): EmailSendingResult
}

sealed class EmailSendingResult {
    object Success : EmailSendingResult()

    class Failure(val errorMessage: String) : EmailSendingResult()
}