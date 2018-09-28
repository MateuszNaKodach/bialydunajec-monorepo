package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageType
import javax.persistence.Embeddable

@Embeddable
class CottageId private constructor(cottageId: String)
    : AggregateId(cottageId) {

    companion object {
        fun ofAcademicMinistryCottage(campEditionId: CampEditionId, academicMinistryId: AcademicMinistryId) =
                CottageId("$campEditionId-${CottageType.ACADEMIC_MINISTRY.name}-$academicMinistryId")

        fun ofStandaloneCottage(campEditionId: CampEditionId) =
                CottageId("$campEditionId-${CottageType.STANDALONE.name}")
    }
}