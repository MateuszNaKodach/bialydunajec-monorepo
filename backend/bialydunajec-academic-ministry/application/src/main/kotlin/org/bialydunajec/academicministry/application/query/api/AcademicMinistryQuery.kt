package org.bialydunajec.academicministry.application.query.api

import org.bialydunajec.ddd.application.base.query.Query

sealed class AcademicMinistryQuery : Query {
    object All : AcademicMinistryQuery()
    object NamesForAll : AcademicMinistryQuery()
    class ById(val academicMinistryId: String) : AcademicMinistryQuery()
    class AllPriestsByAcademicMinistryId(val academicMinistryId: String) : AcademicMinistryQuery()
}
