package org.bialydunajec.registrations.application.query.api

sealed class CottageQuery {
    class All : CottageQuery()
    class ById(val cottageId: String) : CottageQuery()
    class AllByCampRegistrationsEditionId(val campRegistrationsEditionId: String) : CottageQuery()
    class ByIdAndByCampRegistrationsEditionId(val cottageId: String, val campRegistrationsEditionId: String) : CottageQuery()
}