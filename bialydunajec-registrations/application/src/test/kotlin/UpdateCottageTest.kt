import org.assertj.core.api.Assertions.assertThat
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.application.command.CreateAcademicMinistryCottageApplicationService
import org.bialydunajec.registrations.application.command.CreateCampRegistrationsEditionApplicationService
import org.bialydunajec.registrations.application.command.UpdateCottageApplicationService
import org.bialydunajec.registrations.application.command.UpdateCottageConditionsApplicationService
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.query.api.CottageQuery
import org.bialydunajec.registrations.application.query.readmodel.CampRegistrationsDomainModelReader
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.conditions.CottageConditionsDescriptionItem
import org.bialydunajec.registrations.domain.cottage.specification.CottageFreeSpaceSpecificationFactory
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSpace
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class UpdateCottageTest {

    private val campEditionRepository = InMemoryCampRegistrationsEditionRepository
    private val academicMinistryRepository = InMemoryAcademicMinistryRepository
    private val cottageRepository = InMemoryCottageRepository
    private val cottageConditionsRepository = InMemoryCottageConditionsRepository()

    private val editionService = CreateCampRegistrationsEditionApplicationService(campEditionRepository)

    private val ministryCottageService = CreateAcademicMinistryCottageApplicationService(
        campEditionRepository, academicMinistryRepository, cottageRepository
    )

    private val cottageService = UpdateCottageApplicationService(cottageRepository)

    private val cottageConditionsService =
        UpdateCottageConditionsApplicationService(cottageConditionsRepository, cottageRepository)

    private val edition = 1

    private val reader = CampRegistrationsDomainModelReader(
        campEditionRepository,
        academicMinistryRepository,
        cottageRepository,
        InMemoryCampParticipantReadOnlyRepository,
        InMemoryCampEditionShirtReadOnlyRepository,
        CottageFreeSpaceSpecificationFactory(InMemoryCampParticipantRepository),
        cottageConditionsRepository
    )

    @Test
    fun `should retrieve new data after cottage update`() {
        // Given
        val cottageId = createCottage()

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
        val cottageId = createCottage()

        // When
        val newItem = CottageConditionsDescriptionItem(
            "title", "content",
            Url.ExternalUrl("url")
        )
        val newConditions = listOf(newItem)
        val updateCommand = CampRegistrationsCommand.UpdateCottageConditions(cottageId, newConditions)
        cottageConditionsService.execute(updateCommand)

        // Then
        val retrievedCottage = reader.readFor(CottageQuery.NewestByAcademicMinistryId(academicMinistryId))!!
        val retrievedConditionDescriptionItem = retrievedCottage.conditions[0]
        assertThat(retrievedConditionDescriptionItem.title).isEqualTo(newItem.title)
        assertThat(retrievedConditionDescriptionItem.content).isEqualTo(newItem.content)
        assertThat(retrievedConditionDescriptionItem.iconUrl).isEqualTo(newItem.iconUrl.toString())
    }

    private fun createCottage(): CottageId {
        editionService.execute(
            CampRegistrationsCommand.CreateCampRegistrationsEdition(
                CampRegistrationsEditionId(edition), LocalDate.now(), LocalDate.now(), Money(1), null
            )
        )
        return ministryCottageService.execute(
            CampRegistrationsCommand.CreateAcademicMinistryCottage(edition, academicMinistryId)
        )
    }
}
