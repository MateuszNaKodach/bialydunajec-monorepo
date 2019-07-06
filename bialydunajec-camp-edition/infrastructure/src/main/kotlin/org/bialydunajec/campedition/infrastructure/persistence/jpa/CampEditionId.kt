package org.bialydunajec.campedition.infrastructure.persistence.jpa

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class CampEditionId(campEditionNumber: Int) : AggregateId(campEditionNumber.toString())
