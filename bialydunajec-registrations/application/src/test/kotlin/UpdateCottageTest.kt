import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.financial.Money
import org.bialydunajec.registrations.application.command.CreateAcademicMinistryCottageApplicationService
import org.bialydunajec.registrations.application.command.CreateCampRegistrationsEditionApplicationService
import org.bialydunajec.registrations.application.command.UpdateCottageApplicationService
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.academicministry.CampRegistrationsAcademicMinistry
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.cottage.Cottage
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSpace
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageStatus
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

    private val cottageService: UpdateCottageApplicationService =
        UpdateCottageApplicationService(InMemoryCottageRepository)

    private val edition = 1

    @Test
    fun `update cottage should work`() {
        // Given
        editionService.execute(
            CampRegistrationsCommand.CreateCampRegistrationsEdition(
                CampRegistrationsEditionId(edition), LocalDate.now(), LocalDate.now(), Money(1), null
            )
        )
        val cottageId = ministryCottageService.execute(
            CampRegistrationsCommand.CreateAcademicMinistryCottage(
                edition, "a"
            )
        )

        // When
        cottageService.execute(
            CampRegistrationsCommand.UpdateCottage(
                cottageId, "name", null, null, null, CottageSpace(), null, null, null
            )
        )

        // Then
        // todo: get cottage and check if it was updated
    }
}

private object InMemoryAcademicMinistryRepository : AcademicMinistryRepository {

    override fun save(aggregateRoot: CampRegistrationsAcademicMinistry): CampRegistrationsAcademicMinistry {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(aggregateRoot: CampRegistrationsAcademicMinistry) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Collection<CampRegistrationsAcademicMinistry> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(aggregateId: AcademicMinistryId): CampRegistrationsAcademicMinistry? {
        return CampRegistrationsAcademicMinistry(AcademicMinistryId("am1"), "Most", null)
    }

    override fun findByIdAndSpecification(
        aggregateId: AcademicMinistryId,
        specification: Specification<CampRegistrationsAcademicMinistry>
    ): CampRegistrationsAcademicMinistry? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findFirstBySpecification(specification: Specification<CampRegistrationsAcademicMinistry>): CampRegistrationsAcademicMinistry? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllBySpecification(specification: Specification<CampRegistrationsAcademicMinistry>): Collection<CampRegistrationsAcademicMinistry> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(aggregateId: AcademicMinistryId): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByIdAndSpecification(
        aggregateId: AcademicMinistryId,
        specification: Specification<CampRegistrationsAcademicMinistry>
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

private object InMemoryCampRegistrationsEditionRepository : CampRegistrationsEditionRepository {
    private val editions = mutableListOf<CampRegistrationsEdition>()

    override fun save(aggregateRoot: CampRegistrationsEdition): CampRegistrationsEdition {
        editions.add(aggregateRoot)
        return aggregateRoot
    }

    override fun delete(aggregateRoot: CampRegistrationsEdition) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Collection<CampRegistrationsEdition> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(aggregateId: CampRegistrationsEditionId): CampRegistrationsEdition? {
        return editions.find { it.getAggregateId() == aggregateId }
    }

    override fun findByIdAndSpecification(
        aggregateId: CampRegistrationsEditionId,
        specification: Specification<CampRegistrationsEdition>
    ): CampRegistrationsEdition? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findFirstBySpecification(specification: Specification<CampRegistrationsEdition>): CampRegistrationsEdition? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllBySpecification(specification: Specification<CampRegistrationsEdition>): Collection<CampRegistrationsEdition> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(aggregateId: CampRegistrationsEditionId): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByIdAndSpecification(
        aggregateId: CampRegistrationsEditionId,
        specification: Specification<CampRegistrationsEdition>
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

private object InMemoryCottageRepository : CottageRepository {

    private val items = mutableListOf<Cottage>()

    override fun findAllByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Collection<Cottage> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllByCampRegistrationsEditionIdAndStatus(
        campRegistrationsEditionId: CampRegistrationsEditionId,
        status: CottageStatus
    ): Collection<Cottage> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByIdAndCampRegistrationsEditionId(
        cottageId: CottageId,
        campRegistrationsEditionId: CampRegistrationsEditionId
    ): Cottage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun countByCampRegistrationsEditionId(campRegistrationsEditionId: CampRegistrationsEditionId): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findNewestCottageByAcademicMinistryId(academicMinistryId: AcademicMinistryId): Cottage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(aggregateRoot: Cottage): Cottage {
        items.add(aggregateRoot)
        return aggregateRoot
    }

    override fun delete(aggregateRoot: Cottage) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Collection<Cottage> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(aggregateId: CottageId): Cottage? {
        return items.find { it.getAggregateId() == aggregateId }
    }

    override fun findByIdAndSpecification(aggregateId: CottageId, specification: Specification<Cottage>): Cottage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findFirstBySpecification(specification: Specification<Cottage>): Cottage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllBySpecification(specification: Specification<Cottage>): Collection<Cottage> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(aggregateId: CottageId): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByIdAndSpecification(aggregateId: CottageId, specification: Specification<Cottage>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
