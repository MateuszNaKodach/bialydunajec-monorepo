package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantFactory
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CampParticipantRegistrationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantRepository: CampParticipantRepository
) : ApplicationService<CampRegistrationsCommand.RegisterCampParticipantCommand> {

    override fun process(command: CampRegistrationsCommand.RegisterCampParticipantCommand) =
            campParticipantFactory.createCampParticipant(command.camperApplication)
                    .let { campParticipantRepository.save(it) }
                    .getAggregateId()

}


@Service
@Transactional
internal class CampParticipantRegistrationConfirmationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantRepository: CampParticipantRepository
) : ApplicationService<CampRegistrationsCommand.RegisterCampParticipantCommand> {

    override fun process(command: CampRegistrationsCommand.RegisterCampParticipantCommand) =
            campParticipantFactory.createCampParticipant(command.camperApplication)
                    .let { campParticipantRepository.save(it) }
                    .getAggregateId()

}