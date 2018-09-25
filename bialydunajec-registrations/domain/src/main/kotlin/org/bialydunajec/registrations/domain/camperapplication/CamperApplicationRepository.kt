package org.bialydunajec.registrations.domain.camperapplication

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CamperApplicationRepository : JpaRepository<CamperApplication, CamperApplicationId>