package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class EmailAddressDomainRule : DomainRule {
    EMAIL_ADDRESS_TO_UPDATE_MUST_EXISTS;

    override fun getRuleName() = name
    override fun getDescription() = null
}