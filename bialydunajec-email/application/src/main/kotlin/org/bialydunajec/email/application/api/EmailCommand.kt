package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.EmailGroupId
import org.bialydunajec.email.domain.EmailId
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

sealed class EmailCommand : Command {

    class CatalogizeEmail(
        val emailAddress: EmailAddress,
        val emailGroupId: EmailGroupId?,
        val emailOwner: EmailAddressOwner
    ) : Command {
        val emailAddressId: EmailId = EmailId.from(emailAddress, emailGroupId)
    }

    class ChangeEmailAddress(
        val oldEmailAddress: EmailAddress,
        val emailGroupId: EmailGroupId?,
        val newEmailAddress: EmailAddress
    ) : Command {
        val emailAddressId: EmailId = EmailId.from(oldEmailAddress, emailGroupId)
    }

    class CorrectEmailOwner(
        val emailAddress: EmailAddress,
        val emailGroupId: EmailGroupId?,
        val emailOwner: EmailAddressOwner
    ) : Command {
        val emailAddressId: EmailId = EmailId.from(emailAddress, emailGroupId)
    }
}
