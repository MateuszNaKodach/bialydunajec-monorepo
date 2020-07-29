package org.bialydunajec.email.domain.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import javax.persistence.Embeddable

@Embeddable
data class EmailAddressOwner(
        val firstName: FirstName,
        val lastName: LastName
)
