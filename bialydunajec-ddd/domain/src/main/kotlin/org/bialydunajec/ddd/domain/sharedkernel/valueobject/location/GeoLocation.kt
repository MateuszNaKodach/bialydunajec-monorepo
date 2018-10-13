package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@Embeddable
data class GeoLocation(
        val latitude: Double?,
        val longitude: Double?
) : ValueObject