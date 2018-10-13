package org.bialydunajec.registrations.application.query.api

sealed class AcademicMinistryQuery {
    class All : AcademicMinistryQuery()
    class ById(val academicMinistryId: String) : AcademicMinistryQuery()
}