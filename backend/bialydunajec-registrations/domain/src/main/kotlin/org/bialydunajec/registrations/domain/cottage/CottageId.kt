package org.bialydunajec.registrations.domain.cottage

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageType
import java.util.*
import javax.persistence.Embeddable

@Embeddable
class CottageId constructor(cottageId: String)
    : AggregateId(cottageId) {

    companion object {
        fun ofAcademicMinistryCottage(campRegistrationsEditionId: CampRegistrationsEditionId, academicMinistryId: AcademicMinistryId) =
                CottageId("$campRegistrationsEditionId-${CottageType.ACADEMIC_MINISTRY.name}-$academicMinistryId")

        fun ofStandaloneCottage(campRegistrationsEditionId: CampRegistrationsEditionId) =
                CottageId("$campRegistrationsEditionId-${CottageType.STANDALONE.name}-${UUID.randomUUID()}")
    }
}