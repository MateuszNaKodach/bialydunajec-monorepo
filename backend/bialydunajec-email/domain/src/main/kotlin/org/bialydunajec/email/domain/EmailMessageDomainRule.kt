package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRule

enum class EmailMessageDomainRule : DomainRule {
    EMAIL_MESSAGE_TO_RESEND_MUST_EXISTS,
    EMAIL_MESSAGE_WAS_ALREADY_SUCCESSFULLY_SENT,
    EMAIL_MESSAGE_TO_DUPLICATE_MUST_EXISTS;

    override fun getRuleName() = name
    override fun getDescription() = null
}
