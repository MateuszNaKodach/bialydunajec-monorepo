package org.bialydunajec.ddd.domain.sharedkernel.valueobject.camp.camper

enum class MainCadreRole : CampStuffRole {
    SZEF_OBOZU {
        override fun getRoleName() = "Szef Obozu"
    },
    RZECZNIK_OBOZU {
        override fun getRoleName() = "Rzecznik Obozu"
    },
    KWATERMISTRZ_OBOZU {
        override fun getRoleName() = "Kwatermistrz Obozu"
    },
    SZEF_TURYSTYCZNYCH {
        override fun getRoleName() = "Szef Turystycznych"
    },
    SZEF_KULTURALNYCH {
        override fun getRoleName() = "Szef Kulturalnych"
    };

    override fun isCottageCadreMember() = false
    override fun isMainCadreMember() = true
}