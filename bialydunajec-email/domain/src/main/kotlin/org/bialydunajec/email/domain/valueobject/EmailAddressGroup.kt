package org.bialydunajec.email.domain.valueobject

import javax.persistence.Embeddable

@Embeddable
class EmailAddressGroup(
        val name: String
)