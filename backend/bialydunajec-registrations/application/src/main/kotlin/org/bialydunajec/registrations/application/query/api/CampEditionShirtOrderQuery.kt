package org.bialydunajec.registrations.application.query.api

sealed class CampEditionShirtOrderQuery {
    class All : CampEditionShirtOrderQuery()
    class AllByCampRegistrationsEditionId(val campRegistrationsEditionId: String) : CampEditionShirtOrderQuery()
}