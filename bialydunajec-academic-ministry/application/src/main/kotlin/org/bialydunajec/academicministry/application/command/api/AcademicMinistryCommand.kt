package org.bialydunajec.academicministry.application.command.api

import org.bialydunajec.academicministry.domain.academicministry.valueobject.SocialMedia
import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription

sealed class AcademicMinistryCommand : Command {
    data class CreateAcademicMinistry internal constructor(
            val officialName: String,
            val shortName: String,
            val logoImageUrl: Url?,
            val place: Place?,
            val socialMedia: SocialMedia,
            val emailAddress: EmailAddress?,
            val photoUrl: Url?,
            val description: ExtendedDescription?
    ) : AcademicMinistryCommand()

    data class UpdateAcademicMinistry internal constructor(
            val officialName: String,
            val shortName: String,
            val logoImageUrl: Url?,
            val place: Place?,
            val socialMedia: SocialMedia,
            val emailAddress: EmailAddress?,
            val photoUrl: Url?,
            val description: ExtendedDescription?
    ) : AcademicMinistryCommand()
}