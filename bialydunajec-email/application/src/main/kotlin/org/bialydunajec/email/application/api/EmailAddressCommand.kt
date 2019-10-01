package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.EmailAddressId
import org.bialydunajec.email.domain.valueobject.EmailAddressGroup
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

sealed class EmailAddressCommand : Command {

    class CatalogizeEmailAddress(
            val emailAddress: EmailAddress,
            val emailGroup: EmailAddressGroup,
            val emailAddressOwner: EmailAddressOwner) : Command

    class UpdateEmailAddress(
            val emailAddressId: EmailAddressId,
            val newEmailAddress: EmailAddress) : Command
}
