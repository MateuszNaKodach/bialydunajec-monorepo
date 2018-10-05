package org.bialydunajec.academicministry.domain.academicministry.valueobject

import org.bialydunajec.academicministry.domain.academicministry.AcademicMinistryId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription

data class AcademicMinistrySnapshot(
        val academicMinistryId: AcademicMinistryId,
        val officialName: String,
        val shortName: String,
        val logoImageUrl: Url?,
        val place: Place?,
        val socialMedia: SocialMedia,
        val emailAddress: EmailAddress?,
        val photoUrl: Url?,
        val description: ExtendedDescription?
)