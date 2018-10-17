package org.bialydunajec.ddd.application.base.email

interface EmailMessageSender {

    fun sendEmailMessage(message: EmailMessage)
}