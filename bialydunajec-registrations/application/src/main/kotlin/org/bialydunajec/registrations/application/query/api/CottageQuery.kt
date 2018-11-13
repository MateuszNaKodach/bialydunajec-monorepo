package org.bialydunajec.registrations.application.query.api

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender

sealed class CottageQuery {
    class All : CottageQuery()
    class ById(val cottageId: String) : CottageQuery()
    class AllByCampRegistrationsEditionId(val campRegistrationsEditionId: String) : CottageQuery()
    class AllActiveByCampRegistrationsEditionId(val campRegistrationsEditionId: String, val camperGender: Gender) : CottageQuery()
    class ByIdAndByCampRegistrationsEditionId(val cottageId: String, val campRegistrationsEditionId: String) : CottageQuery()
    class NewestByAcademicMinistryId(val academicMinistryId: String) : CottageQuery()
}