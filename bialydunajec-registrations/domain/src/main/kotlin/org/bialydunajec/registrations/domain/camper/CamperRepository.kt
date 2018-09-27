package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.aggregate.DomainRepository

interface CamperRepository : DomainRepository<Camper, CamperId>