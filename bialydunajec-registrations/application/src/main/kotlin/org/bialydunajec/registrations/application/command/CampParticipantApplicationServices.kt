package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantFactory
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CampParticipantRegistrationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantRepository: CampParticipantRepository,
        private val campEditionShirtRepository: CampEditionShirtRepository
) : ApplicationService<CampRegistrationsCommand.RegisterCampParticipantCommand> {

    override fun process(command: CampRegistrationsCommand.RegisterCampParticipantCommand): CampParticipantId =
            campParticipantFactory.createCampParticipant(command.campRegistrationsEditionId, command.camperApplication)
                    .let { campParticipantRepository.save(it) }
                    .also {
                        val campEditionShirt = campEditionShirtRepository.findByCampRegistrationsEditionId(command.campRegistrationsEditionId)
                        campEditionShirt?.placeOrder(it, command.shirtOrder.shirtColorOptionId, command.shirtOrder.shirtSizeOptionId)
                    }.getAggregateId()


}