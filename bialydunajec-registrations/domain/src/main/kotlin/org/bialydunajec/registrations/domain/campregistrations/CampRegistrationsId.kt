package org.bialydunajec.registrations.domain.campregistrations

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import javax.persistence.Embeddable

@Embeddable
class CampRegistrationsId(campEditionId: CampEditionId) : AggregateId(campEditionId.toString())