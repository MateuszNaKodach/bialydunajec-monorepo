package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageType
import javax.persistence.Embeddable

@Embeddable
class CottageId private constructor(cottageId: String)
    : AggregateId(cottageId) {

    companion object {
        fun ofAcademicMinistryCottage(campRegistrationsId: CampRegistrationsId, academicMinistryId: AcademicMinistryId) =
                CottageId("$campRegistrationsId-${CottageType.ACADEMIC_MINISTRY.name}-$academicMinistryId")

        fun ofStandaloneCottage(campRegistrationsId: CampRegistrationsId) =
                CottageId("$campRegistrationsId-${CottageType.STANDALONE.name}")
    }
}