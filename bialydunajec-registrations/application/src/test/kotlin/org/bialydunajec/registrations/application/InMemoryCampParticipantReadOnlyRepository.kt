package org.bialydunajec.registrations.application

import org.bialydunajec.ddd.domain.base.specification.Specification
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipant
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantReadOnlyRepository
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

internal class InMemoryCampParticipantReadOnlyRepository : CampParticipantReadOnlyRepository {

    override fun findAll(pageable: Pageable): Page<CampParticipant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Collection<CampParticipant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllByCottageId(cottageId: CottageId): Collection<CampParticipant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllByCottageId(cottageId: CottageId, pageable: Pageable): Page<CampParticipant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllByCampRegistrationsEditionId(
        campRegistrationsEditionId: CampRegistrationsEditionId,
        pageable: Pageable
    ): Page<CampParticipant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun countByCottageId(cottageId: CottageId): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun countByCottageIdAndGender(cottageId: CottageId, gender: Gender): Long {
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
