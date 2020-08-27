package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable

@Embeddable
data class GeoLocation(
        val latitude: Double?,
        val longitude: Double?
) : ValueObject
