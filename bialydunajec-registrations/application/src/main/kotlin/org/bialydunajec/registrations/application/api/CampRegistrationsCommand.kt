package org.bialydunajec.registrations.application.api

import org.bialydunajec.ddd.application.base.Command
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.ZonedDateTimeRange
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.entity.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.CottageId

sealed class CampRegistrationsCommand : Command {
    data class SetupCampRegistrations(
            val campEditionId: CampEditionId,
            val registrationsDuration: ZonedDateTimeRange
    ) : CampRegistrationsCommand()

    data class CreateAcademicMinistryCottage(
            val campEditionId: CampEditionId,
            val academicMinistryId: AcademicMinistryId
    ) : CampRegistrationsCommand()

    data class CreateStandaloneCottage(
            val campEditionId: CampEditionId,
            val cottageName: String
    ) : CampRegistrationsCommand()
}