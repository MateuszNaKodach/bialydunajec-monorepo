import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.academicministry.CampRegistrationsAcademicMinistry

internal const val academicMinistryId = "am1"

internal class InMemoryAcademicMinistryRepository : AcademicMinistryRepository {

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
        return CampRegistrationsAcademicMinistry(AcademicMinistryId(academicMinistryId), "Most", null)
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
