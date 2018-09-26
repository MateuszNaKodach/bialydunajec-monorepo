package org.bialydunajec.ddd.domain.sharedkernel.valueobject

import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@Embeddable
data class GeoLocation(
        @NotNull
        val latitude: Double,
        @NotNull
        val longitude: Double
)