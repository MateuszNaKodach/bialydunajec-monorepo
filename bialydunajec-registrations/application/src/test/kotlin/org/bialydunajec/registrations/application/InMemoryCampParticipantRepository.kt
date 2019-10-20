package org.bialydunajec.registrations.application

import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.cottage.CottageId

internal class InMemoryCampParticipantRepository : CampParticipantRepository {

    override fun findAllByCottageId(cottageId: CottageId): Collection<CampParticipant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun countByCottageId(cottageId: CottageId): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByPeselAndCampRegistrationsEditionId(
        pesel: Pesel,
        campRegistrationsEditionId: CampRegistrationsEditionId
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(aggregateRoot: CampParticipant): CampParticipant {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(aggregateRoot: CampParticipant) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Collection<CampParticipant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(aggregateId: CampParticipantId): CampParticipant? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByIdAndSpecification(
        aggregateId: CampParticipantId,
        specification: Specification<CampParticipant>
    ): CampParticipant? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findFirstBySpecification(specification: Specification<CampParticipant>): CampParticipant? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllBySpecification(specification: Specification<CampParticipant>): Collection<CampParticipant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(aggregateId: CampParticipantId): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsByIdAndSpecification(
        aggregateId: CampParticipantId,
        specification: Specification<CampParticipant>
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
