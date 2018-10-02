package org.bialydunajec.registrations.domain.camper

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface CamperRepository : DomainRepository<Camper, CamperId>