package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class CampEditionId(editionNumber: Int) : AggregateId(editionNumber.toString())