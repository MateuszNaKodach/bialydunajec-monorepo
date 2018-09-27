package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.aggregate.DomainRepository
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Pesel
import org.bialydunajec.registrations.domain.cottage.CottageId

interface CamperRepository : DomainRepository<Camper, CamperId>