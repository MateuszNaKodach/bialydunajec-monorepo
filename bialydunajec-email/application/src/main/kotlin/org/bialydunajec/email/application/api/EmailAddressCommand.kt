package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.email.domain.EmailGroupId
import org.bialydunajec.email.domain.EmailId
import org.bialydunajec.email.domain.valueobject.EmailAddressOwner

sealed class EmailAddressCommand : Command {

    class CatalogizeEmailAddress(
        val email: EmailAddress,
        val emailGroupId: EmailGroupId?,
        val emailOwner: EmailAddressOwner
    ) : Command {
        val emailAddressId: EmailId = EmailId.from(email, emailGroupId)
    }

    class ChangeEmailAddressValue(
        val oldEmail: EmailAddress,
        val emailGroupId: EmailGroupId?,
        val newEmail: EmailAddress
    ) : Command {
        val emailAddressId: EmailId = EmailId.from(oldEmail, emailGroupId)
    }

    class UpdateEmailAddressOwner(
        val email: EmailAddress,
        val emailGroupId: EmailGroupId?,
        val emailOwner: EmailAddressOwner
    ) : Command {
        val emailAddressId: EmailId = EmailId.from(email, emailGroupId)
    }
}
