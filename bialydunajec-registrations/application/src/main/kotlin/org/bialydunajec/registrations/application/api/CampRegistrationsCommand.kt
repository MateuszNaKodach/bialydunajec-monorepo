package org.bialydunajec.registrations.application.api

import org.bialydunajec.ddd.application.base.Command
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.cottage.CottageId

sealed class CampRegistrationsCommand(campRegistrationsId: CampRegistrationsId) : Command<CampRegistrationsId>(campRegistrationsId){
    class CamperRegistrationCommand(
            campRegistrationsId: CampRegistrationsId,
            val cottageId: CottageId
    ) : CampRegistrationsCommand(campRegistrationsId)
}