package org.bialydunajec.email.domain

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import javax.persistence.Embeddable

const val DEFAULT_EMAIL_GROUP_ID: String = "DEFAULT_GROUP"

@Embeddable
class EmailGroupId(emailGroupId: String = DEFAULT_EMAIL_GROUP_ID) : AggregateId(emailGroupId)
