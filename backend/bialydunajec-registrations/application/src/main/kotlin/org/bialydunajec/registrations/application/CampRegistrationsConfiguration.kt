package org.bialydunajec.registrations.application

import org.bialydunajec.ddd.application.base.time.Clock
import org.bialydunajec.registrations.application.command.*
import org.bialydunajec.registrations.application.command.CreateCampRegistrationsEditionApplicationService
import org.bialydunajec.registrations.application.command.FinishCampRegistrationsNowApplicationService
import org.bialydunajec.registrations.application.command.SetupCampRegistrationsApplicationService
import org.bialydunajec.registrations.application.command.StartCampRegistrationsNowApplicationService
import org.bialydunajec.registrations.application.command.UpdateCampRegistrationsEditionDurationApplicationService
import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.query.api.CampRegistrationsQueryGateway
import org.bialydunajec.registrations.application.query.readmodel.CampRegistrationsDomainModelReader
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.camper.campparticipant.*
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.cottage.specification.CottageFreeSpaceSpecificationFactory
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountFactory
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountRepository
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.bialydunajec.registrations.domain.shirt.ShirtOrderRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class CampRegistrationsConfiguration(
        private val clock: Clock,
        private val campRegistrationsEditionRepository: CampRegistrationsEditionRepository,
        private val academicMinistryRepository: AcademicMinistryRepository,
        private val cottageRepository: CottageRepository,
        private val campParticipantRepository: CampParticipantRepository,
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository,
        private val campEditionShirtRepository: CampEditionShirtRepository,
        private val campParticipantCottageAccountRepository: CampParticipantCottageAccountRepository,
        private val shirtOrderRepository: ShirtOrderRepository,
        private val peselEncoder: PeselEncoder,
) {

    @Bean
    fun campRegistrationsCommandGateway(): CampRegistrationsCommandGateway {
        val campParticipantFactory = CampParticipantFactory(
                cottageRepository,
                campRegistrationsEditionRepository,
                CottageFreeSpaceSpecificationFactory(campParticipantRepository),
                campParticipantRepository,
                CamperTrackingCodeGenerator(peselEncoder)
        )
        val campParticipantRegistrationApplicationService = CampParticipantRegistrationApplicationService(
                campParticipantFactory,
                CampParticipantCottageAccountFactory(campRegistrationsEditionRepository),
                campParticipantRepository,
                campEditionShirtRepository,
                shirtOrderRepository,
                campParticipantRegistrationRepository,
                campParticipantCottageAccountRepository
        )
        val campParticipantRegistrationConfirmApplicationService = VerifyCampParticipantRegistrationApplicationService(campParticipantRegistrationRepository)
        return CampRegistrationsCommandGateway(campParticipantRegistrationApplicationService, campParticipantRegistrationConfirmApplicationService)
    }

    @Bean
    fun campRegistrationsAdminCommandGateway(): CampRegistrationsAdminCommandGateway {
        val createCampRegistrationsEditionApplicationService = CreateCampRegistrationsEditionApplicationService(campRegistrationsEditionRepository)
        val updateCampRegistrationsEditionDurationApplicationService = UpdateCampRegistrationsEditionDurationApplicationService(campRegistrationsEditionRepository)
        val setupCampRegistrationsApplicationService = SetupCampRegistrationsApplicationService(campRegistrationsEditionRepository, clock)
        val startCampRegistrationsNowApplicationService = StartCampRegistrationsNowApplicationService(campRegistrationsEditionRepository, clock)
        val finishCampRegistrationsNowApplicationService = FinishCampRegistrationsNowApplicationService(campRegistrationsEditionRepository, clock)
        val suspendCampRegistrationsNowApplicationService = SuspendCampRegistrationsNowApplicationService(campRegistrationsEditionRepository, clock)
        val unsuspendCampRegistrationsNowApplicationService = UnsuspendCampRegistrationsNowApplicationService(campRegistrationsEditionRepository, clock)

        val createAcademicMinistryCottageApplicationService = CreateAcademicMinistryCottageApplicationService(
                campRegistrationsEditionRepository,
                academicMinistryRepository,
                cottageRepository
        )
        val createStandaloneCottageApplicationService = CreateStandaloneCottageApplicationService(
                campRegistrationsEditionRepository,
                cottageRepository
        )
        val updateCottageApplicationService = UpdateCottageApplicationService(cottageRepository)
        val activateCottageApplicationService = ActivateCottageApplicationService(cottageRepository)
        val deactivateCottageApplicationService = DeactivateCottageApplicationService(cottageRepository)
        val deleteCottageApplicationService = DeleteCottageApplicationService(cottageRepository, campParticipantRepository)

        val verifyCampParticipantRegistrationByAuthorizedApplicationService = VerifyCampParticipantRegistrationByAuthorizedApplicationService(campParticipantRegistrationRepository)

        val unregisterCampParticipantApplicationService = UnregisterCampParticipantApplicationService(campParticipantRepository)
        val correctCampParticipantRegistrationDataApplicationService = CorrectCampParticipantRegistrationDataApplicationService(campParticipantRepository)
        val updateCampEditionShirtApplicationService = UpdateCampEditionShirtApplicationService(campEditionShirtRepository)
        val addCampEditionShirtColorOptionApplicationService = AddCampEditionShirtColorOptionApplicationService(campEditionShirtRepository)
        val addCampEditionShirtSizeOptionApplicationService = AddCampEditionShirtSizeOptionApplicationService(campEditionShirtRepository)

        val updateCampEditionShirtSizeOptionApplicationService = UpdateCampEditionShirtSizeOptionApplicationService(campEditionShirtRepository)
        val updateCampEditionShirtColorOptionApplicationService = UpdateCampEditionShirtColorOptionApplicationService(campEditionShirtRepository)

        val payCommitmentAndDepositMoneyApplicationService = PayCommitmentAndDepositMoneyApplicationService(campParticipantCottageAccountRepository)

        return CampRegistrationsAdminCommandGateway(
                createCampRegistrationsEditionApplicationService,
                updateCampRegistrationsEditionDurationApplicationService,
                setupCampRegistrationsApplicationService,
                startCampRegistrationsNowApplicationService,
                finishCampRegistrationsNowApplicationService,
                suspendCampRegistrationsNowApplicationService,
                unsuspendCampRegistrationsNowApplicationService,
                createAcademicMinistryCottageApplicationService,
                createStandaloneCottageApplicationService,
                updateCottageApplicationService,
                activateCottageApplicationService,
                deactivateCottageApplicationService,
                deleteCottageApplicationService,
                verifyCampParticipantRegistrationByAuthorizedApplicationService,
                unregisterCampParticipantApplicationService,
                correctCampParticipantRegistrationDataApplicationService,
                updateCampEditionShirtApplicationService,
                addCampEditionShirtColorOptionApplicationService,
                addCampEditionShirtSizeOptionApplicationService,
                updateCampEditionShirtSizeOptionApplicationService,
                updateCampEditionShirtColorOptionApplicationService,
                payCommitmentAndDepositMoneyApplicationService
        )
    }

    @Bean
    fun campRegistrationsQueryGateway(): CampRegistrationsQueryGateway {
        val domainModelReader = CampRegistrationsDomainModelReader(
                campRegistrationsEditionRepository,
                academicMinistryRepository,
                cottageRepository,
                campParticipantRepository,
                campEditionShirtRepository,
                CottageFreeSpaceSpecificationFactory(campParticipantRepository)
        )
        return CampRegistrationsQueryGateway(domainModelReader)
    }


}
