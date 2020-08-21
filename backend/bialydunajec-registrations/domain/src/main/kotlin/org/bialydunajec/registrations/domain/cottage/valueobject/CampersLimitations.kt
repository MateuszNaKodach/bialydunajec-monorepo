package org.bialydunajec.registrations.domain.cottage.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.AgeRange
import javax.persistence.Embeddable
import javax.persistence.Embedded

const val DEFAULT_MIN_CAMPER_AGE = 18
const val DEFAULT_MAX_CAMPER_AGE = 26

/*
Describes acceptance camper criteria for cottage accommodation
 */
@Embeddable
data class CampersLimitations(
        @Embedded
        val ageRange: AgeRange? = AgeRange(DEFAULT_MIN_CAMPER_AGE, DEFAULT_MAX_CAMPER_AGE)
)