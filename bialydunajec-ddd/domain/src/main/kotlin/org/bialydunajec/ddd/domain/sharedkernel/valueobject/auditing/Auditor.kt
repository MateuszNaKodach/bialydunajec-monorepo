package org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.validation.constraints.NotNull

//TODO: Change to only userId
@Embeddable
data class Auditor(
        @Embedded
        @NotNull
        val auditorId: AggregateId?,

        @Embedded
        @NotNull
        val firstName: FirstName?,

        @Embedded
        @NotNull
        val lastName: LastName?,

        @Embedded
        @NotNull
        val emailAddress: EmailAddress?
) : ValueObject