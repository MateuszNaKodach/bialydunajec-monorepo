import org.assertj.core.api.Assertions.assertThat
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.application.command.CreateAcademicMinistryCottageApplicationService
import org.bialydunajec.registrations.application.command.CreateCampRegistrationsEditionApplicationService
import org.bialydunajec.registrations.application.command.UpdateCottageApplicationService
import org.bialydunajec.registrations.application.command.UpdateCottageConditionsApplicationService
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.query.api.CottageQuery
import org.bialydunajec.registrations.application.query.readmodel.CampRegistrationsDomainModelReader
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.conditions.InMemoryCottageConditionsRepository
import org.bialydunajec.registrations.domain.cottage.specification.CottageFreeSpaceSpecificationFactory
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSpace
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class UpdateCottageTest {

    private val editionService =
        CreateCampRegistrationsEditionApplicationService(InMemoryCampRegistrationsEditionRepository)

    private val ministryCottageService = CreateAcademicMinistryCottageApplicationService(
        InMemoryCampRegistrationsEditionRepository,
        InMemoryAcademicMinistryRepository,
        InMemoryCottageRepository
    )

    private val cottageService = UpdateCottageApplicationService(InMemoryCottageRepository)

    private val cottageConditionsRepository = InMemoryCottageConditionsRepository()

    private val cottageConditionsService = UpdateCottageConditionsApplicationService(cottageConditionsRepository)

    private val edition = 1

    private val reader = CampRegistrationsDomainModelReader(
        InMemoryCampRegistrationsEditionRepository,
        InMemoryAcademicMinistryRepository,
        InMemoryCottageRepository,
        InMemoryCampParticipantReadOnlyRepository,
        InMemoryCampEditionShirtReadOnlyRepository,
        CottageFreeSpaceSpecificationFactory(InMemoryCampParticipantRepository),
        cottageConditionsRepository
    )

    @Test
    fun `should retrieve new data after cottage update`() {
        // Given
        editionService.execute(
            CampRegistrationsCommand.CreateCampRegistrationsEdition(
                CampRegistrationsEditionId(edition), LocalDate.now(), LocalDate.now(), Money(1), null
            )
        )
        val cottageId = ministryCottageService.execute(
            CampRegistrationsCommand.CreateAcademicMinistryCottage(edition, academicMinistryId)
        )

        // When
        val updateCommand = CampRegistrationsCommand.UpdateCottage(
            cottageId, "name", null, null, null, CottageSpace(), null, null, null
        )
        cottageService.execute(updateCommand)

        // Then
        val retrievedCottage = reader.readFor(CottageQuery.NewestByAcademicMinistryId(academicMinistryId))!!
        assertThat(retrievedCottage.name).isEqualTo(updateCommand.name)
    }

    @Test
    fun `should retrieve new data after cottage conditions update`() {
        // Given
        editionService.execute(
            CampRegistrationsCommand.CreateCampRegistrationsEdition(
                CampRegistrationsEditionId(edition), LocalDate.now(), LocalDate.now(), Money(1), null
            )
        )
        val cottageId = ministryCottageService.execute(
            CampRegistrationsCommand.CreateAcademicMinistryCottage(edition, academicMinistryId)
        )

        // When
        val newConditions = "temporary conditions description"
        val updateCommand = CampRegistrationsCommand.UpdateCottageConditions(cottageId, newConditions)
        cottageConditionsService.execute(updateCommand)

        // Then
        val retrievedCottage = reader.readFor(CottageQuery.NewestByAcademicMinistryId(academicMinistryId))!!
        assertThat(retrievedCottage.conditions).isEqualTo(newConditions)
    }
}
