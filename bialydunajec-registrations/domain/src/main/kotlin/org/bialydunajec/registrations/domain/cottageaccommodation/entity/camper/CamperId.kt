package org.bialydunajec.registrations.domain.cottageaccommodation.entity.camper

import org.bialydunajec.ddd.domain.base.valueobject.EntityId
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Pesel
import javax.persistence.Embeddable

@Embeddable
class CamperId(pesel: Pesel? = null) : EntityId(if (pesel == null) EntityId.defaultValue() else pesel.toString())