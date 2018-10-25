package org.bialydunajec.academicministry.application.query.api

sealed class AcademicPriestQuery {
    class AllByAcademicMinistryId(val academicMinistryId: String) : AcademicPriestQuery()
}