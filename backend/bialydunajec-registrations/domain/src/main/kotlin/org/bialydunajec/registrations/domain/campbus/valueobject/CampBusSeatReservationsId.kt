package org.bialydunajec.registrations.domain.campbus.valueobject

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class CampBusSeatReservationsId(campBusLineId: String = defaultValue()) : AggregateId(campBusLineId)