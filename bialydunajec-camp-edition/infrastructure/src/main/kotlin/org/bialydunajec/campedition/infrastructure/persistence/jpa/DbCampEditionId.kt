package org.bialydunajec.campedition.infrastructure.persistence.jpa

import org.bialydunajec.ddd.domain.base.valueobject.DbAggregateId
import javax.persistence.Embeddable

@Embeddable
class DbCampEditionId(campEditionNumber: Int) : DbAggregateId(campEditionNumber.toString())
