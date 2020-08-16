package org.bialydunajec.academicministry.messages.event


sealed class AcademicMinistryExternalEvent(
        val academicMinistryId: String
) {

    class AcademicMinistryCreated(
            academicMinistryId: String,
            val officialName: String,
            val shortName: String?,
            val logoImageUrl: String?
    ) : AcademicMinistryExternalEvent(academicMinistryId)

    class AcademicMinistryUpdated(
            academicMinistryId: String,
            val officialName: String,
            val shortName: String?,
            val logoImageUrl: String?
    ) : AcademicMinistryExternalEvent(academicMinistryId)
}