package org.bialydunajec.email.domain.valueobject

import javax.persistence.Embeddable

@Embeddable
class EmailGroupName(
        val raw: String
){
        override fun toString() = raw
}
