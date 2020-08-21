package org.bialydunajec.registrations.domain.cottage.valueobject

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.auditing.Audit
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.CottageId

data class CottageSnapshot internal constructor(
        val cottageId: CottageId,
        val campRegistrationsEditionId: CampRegistrationsEditionId,
        val cottageType: CottageType,
        val academicMinistryId: AcademicMinistryId?,
        val name: String,
        val logoImageUrl: Url?,
        val buildingPhotoUrl: Url?,
        val place: Place?,
        val cottageSpace: CottageSpace?,
        val campersLimitations: CampersLimitations?,
        val bankTransferDetails: BankTransferDetails?,
        val cottageState: CottageStatus,
        val cottageBoss: CottageBoss?,
        val audit: Audit
)