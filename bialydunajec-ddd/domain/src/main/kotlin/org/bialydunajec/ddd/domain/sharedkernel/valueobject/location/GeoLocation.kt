package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@Embeddable
data class GeoLocation(
        @NotNull
        val latitude: Double,
        @NotNull
        val longitude: Double
) : ValueObject