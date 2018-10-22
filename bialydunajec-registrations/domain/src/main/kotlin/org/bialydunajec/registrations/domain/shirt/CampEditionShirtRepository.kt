package org.bialydunajec.registrations.domain.shirt

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface CampEditionShirtRepository : DomainRepository<CampEditionShirt, CampEditionShirtId>, CampEditionShirtReadOnlyRepository