package org.bialydunajec.ddd.domain.sharedkernel.valueobject.camp.camper

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject

interface CampStuffRole : ValueObject {
    fun getRoleName(): String
    fun isMainCadreMember(): Boolean
    fun isCottageCadreMember(): Boolean
}
