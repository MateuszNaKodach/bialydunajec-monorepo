package org.bialydunajec.ddd.domain.sharedkernel.valueobject.camp.camper

enum class CottageCadreRole : CampStuffRole {
    SZEF_CHATKI {
        override fun getRoleName() = "Szef Chatki"
    },
    GLOWNY_TURYSTYCZNY {
        override fun getRoleName() = "Główny Turystyczny"

    },
    TURYSTYCZNY {
        override fun getRoleName() = "Turystyczny"

    },
    KUCHENNA_KUCHENNY {
        override fun getRoleName() = "Kuchenna / Kuchenny"
    },
    KULTURALNA_KULTURALNY {
        override fun getRoleName() = "Kulturalna / Kultrualny"
    };

    override fun isMainCadreMember() = false

    override fun isCottageCadreMember() = true
}