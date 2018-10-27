package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantFactory
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistration
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
internal class CampParticipantRegistrationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantRepository: CampParticipantRepository,
        private val campEditionShirtRepository: CampEditionShirtRepository,
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository
) : ApplicationService<CampRegistrationsCommand.RegisterCampParticipantCommand> {

    //TODO: Tworzyć też CampParticipantRegistration!!!! I do niego dodawać shirtOrder! Zeby tez bylo mozna przywrocic w razie czego.
    override fun process(command: CampRegistrationsCommand.RegisterCampParticipantCommand): CampParticipantId =
            campParticipantFactory.createCampParticipant(command.campRegistrationsEditionId, command.camperApplication)
                    .let { campParticipantRepository.save(it) }
                    .also {
                        /* FIXME: Coś jest nie tak, bo tworzę i modyfikuję agregat, przez zmianę czegoś, np. dodanie nowej koszulki bedzie blad transakcji
                            zrobić tak, że ShirtOrder jest innym agregatem.
                        */
                        val campEditionShirt = campEditionShirtRepository.findByCampRegistrationsEditionId(command.campRegistrationsEditionId)
                        campEditionShirt?.placeOrder(it, command.shirtOrder.shirtColorOptionId, command.shirtOrder.shirtSizeOptionId)
                    }.also {
                        campParticipantRegistrationRepository.save(CampParticipantRegistration.createFrom(it.getSnapshot()))
                    }
                    .getAggregateId()


}