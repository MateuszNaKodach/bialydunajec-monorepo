package org.bialydunajec.campedition.infrastructure.persistence.jpa

import org.bialydunajec.ddd.domain.base.persistence.DomainRepository

interface CampEditionRepository : DomainRepository<DbCampEdition, DbCampEditionId>
