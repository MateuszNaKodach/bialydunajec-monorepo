package org.bialydunajec.registrations.domain.campregistrations

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class CampRegistrationsId(campEditionNumber: Int) : AggregateId(campEditionNumber.toString())