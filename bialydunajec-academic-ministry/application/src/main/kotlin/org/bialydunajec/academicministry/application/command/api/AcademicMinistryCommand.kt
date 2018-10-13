package org.bialydunajec.academicministry.application.command.api

import org.bialydunajec.academicministry.domain.AcademicMinistryId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.SocialMedia
import org.bialydunajec.ddd.application.base.command.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription

sealed class AcademicMinistryCommand : Command {
    data class CreateAcademicMinistry constructor(
            val officialName: String,
            val shortName: String?,
            val logoImageUrl: Url?,
            val place: Place?,
            val socialMedia: SocialMedia,
            val emailAddress: EmailAddress?,
            val photoUrl: Url?,
            val description: ExtendedDescription?
    ) : AcademicMinistryCommand()

    data class UpdateAcademicMinistry constructor(
            val academicMinistryId: AcademicMinistryId,
            val officialName: String,
            val shortName: String?,
            val logoImageUrl: Url?,
            val place: Place?,
            val socialMedia: SocialMedia,
            val emailAddress: EmailAddress?,
            val photoUrl: Url?,
            val description: ExtendedDescription?
    ) : AcademicMinistryCommand()

    data class InactivateAcademicMinistry internal constructor(
            val academicMinistryId: AcademicMinistryId
    ) : AcademicMinistryCommand()
}