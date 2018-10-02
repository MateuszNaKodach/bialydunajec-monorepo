package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import javax.persistence.Embeddable

@Embeddable
class CamperId(pesel: Pesel? = null) : AggregateId(if (pesel == null) AggregateId.defaultValue() else pesel.hashCode().toString())