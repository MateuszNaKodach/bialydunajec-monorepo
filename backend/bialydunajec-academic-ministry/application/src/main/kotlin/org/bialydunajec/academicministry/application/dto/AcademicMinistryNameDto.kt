package org.bialydunajec.academicministry.application.dto

import org.bialydunajec.academicministry.domain.valueobject.AcademicMinistrySnapshot

data class AcademicMinistryNameDto(
        val academicMinistryId: String,
        val officialName: String,
        val shortName: String?
){
    companion object {
        fun from(snapshot: AcademicMinistrySnapshot) =
                AcademicMinistryNameDto(
                        academicMinistryId = snapshot.academicMinistryId.toString(),
                        officialName = snapshot.officialName,
                        shortName = snapshot.shortName
                )
    }

    fun getDisplayName() = shortName ?: officialName
}