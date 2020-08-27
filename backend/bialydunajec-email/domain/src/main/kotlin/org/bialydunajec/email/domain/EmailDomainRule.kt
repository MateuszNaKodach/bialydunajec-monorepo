package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRule

enum class EmailDomainRule : DomainRule {
    EMAIL_ADDRESS_TO_CHANGE_VALUE_MUST_EXISTS,
    EMAIL_ADDRESS_TO_CORRECT_OWNER_MUST_EXISTS,
    EMAIL_ADDRESS_TO_DEACTIVATE_MUST_BE_ACTIVE,
    EMAIL_ADDRESS_CAN_BE_CATAGOLIZED_ONLY_ONCE_AT_THE_SAME_GROUP;

    override fun getRuleName() = name
    override fun getDescription() = null
}
