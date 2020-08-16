package org.bialydunajec.ddd.application.base.email

interface EmailMessageSenderPort {

    fun sendEmailMessage(message: SimpleEmailMessage)
}