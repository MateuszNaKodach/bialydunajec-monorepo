package org.bialydunajec.registrations.domain.cottage.valueobject

enum class CottageType {
    STANDALONE,
    ACADEMIC_MINISTRY;

    fun isStandalone() = this == STANDALONE
    fun isAcademicMinistry() = this == ACADEMIC_MINISTRY
}