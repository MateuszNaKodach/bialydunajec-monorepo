package org.bialydunajec.email.application.api

import org.bialydunajec.ddd.application.base.command.Command

sealed class EmailAddressCommand : Command {

    class CatalogizeEmailAddress(
            val emailAddress: String,
            val emailOwnerName: String,
            val emailOwnerLastName: String,
            val emailGroupName: String) : Command

    class UpdateEmailAddress(val emailAddressId: String, val newEmailAddress: String) : Command
}
