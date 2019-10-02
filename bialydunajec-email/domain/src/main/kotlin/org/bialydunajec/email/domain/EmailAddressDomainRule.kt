package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class EmailAddressDomainRule : DomainRule {
    EMAIL_ADDRESS_TO_CHANGE_VALUE_MUST_EXISTS,
    EMAIL_ADDRESS_TO_CORRECT_OWNER_MUST_EXISTS,
    EMAIL_ADDRESS_TO_DEACTIVATE_MUST_BE_ACTIVE;

    override fun getRuleName() = name
    override fun getDescription() = null
}
