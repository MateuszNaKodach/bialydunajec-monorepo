package org.bialydunajec.ddd.domain.sharedkernel.valueobject.camp.camper

interface CampStuffRole {
    fun getRoleName(): String
    fun isMainCadreMember(): Boolean
    fun isCottageCadreMember(): Boolean
}