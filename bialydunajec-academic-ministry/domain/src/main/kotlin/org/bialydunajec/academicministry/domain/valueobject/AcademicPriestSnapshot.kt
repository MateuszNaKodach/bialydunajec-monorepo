package org.bialydunajec.academicministry.domain.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.PersonalTitle
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription

data class AcademicPriestSnapshot(
        val firstName: FirstName,
        val lastName: LastName,
        val personalTitle: PersonalTitle?,
        val emailAddress: EmailAddress?,
        val phoneNumber: PhoneNumber?,
        val description: ExtendedDescription?,
        val photoUrl: Url?
)