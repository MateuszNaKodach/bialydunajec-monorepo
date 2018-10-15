package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.CampParticipantFactory
import org.bialydunajec.registrations.domain.camper.CampParticipantRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CampParticipantRegistrationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantRepository: CampParticipantRepository
) : ApplicationService<CampRegistrationsCommand.CampParticipantRegistrationCommand> {

    override fun process(command: CampRegistrationsCommand.CampParticipantRegistrationCommand) =
            campParticipantFactory.createCampParticipant(command.camperApplication)
                    .let { campParticipantRepository.save(it) }
                    .getAggregateId()

}