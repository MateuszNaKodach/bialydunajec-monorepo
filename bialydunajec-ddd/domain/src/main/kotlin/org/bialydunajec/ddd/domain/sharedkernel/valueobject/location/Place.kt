package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.validation.constraints.NotNull

@Embeddable
data class Place(
        @NotNull
        @Embedded
        val address: Address,

        @Embedded
        val geoLocation: GeoLocation? = null
) : ValueObject