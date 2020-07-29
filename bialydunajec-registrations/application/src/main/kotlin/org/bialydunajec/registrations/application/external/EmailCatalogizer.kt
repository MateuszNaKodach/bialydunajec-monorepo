package org.bialydunajec.registrations.application.external

import org.bialydunajec.ddd.application.base.external.command.ExternalCommandBus
import org.bialydunajec.email.messages.command.EmailExternalCommand
import org.bialydunajec.registrations.application.EmailGroupIdGenerator
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.springframework.stereotype.Component

@Component
internal class EmailCatalogizer(
    private val externalCommandBus: ExternalCommandBus,
    private val cottageRepository: CottageRepository
) {

    fun catalogizeEmailFor(camperApplication: CamperApplication){
        val campParticipantCottage = cottageRepository.findById(camperApplication.cottageId)!!
        externalCommandBus.sendAllPayloads(
            EmailExternalCommand.CatalogizeEmail(
                camperApplication.emailAddress.toString(),
                camperApplication.personalData.firstName.toString(),
                camperApplication.personalData.lastName.toString(),
                EmailGroupIdGenerator.campParticipantRegisteredInCottage(campParticipantCottage)
            ).alsoWithGroup(EmailGroupIdGenerator.campParticipantRegisteredOnCampEdition(campParticipantCottage.getCampEditionId()))
        )
    }

}
