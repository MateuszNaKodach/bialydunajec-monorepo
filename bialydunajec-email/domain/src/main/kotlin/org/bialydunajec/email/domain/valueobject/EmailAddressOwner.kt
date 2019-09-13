package org.bialydunajec.email.domain.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName

class EmailAddressOwner(
        val firstName: FirstName,
        val lastName: LastName
)